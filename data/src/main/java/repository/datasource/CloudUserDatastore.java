/**
 * Copyright (C) 2016 Marc Gonzalez Diez Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package repository.datasource;

import android.content.Context;

import com.mgdiez.data.bean.dto.tweet.mapper.UserDtoMapper;
import com.mgdiez.data.bean.vo.tweet.UserVo;
import com.mgdiez.data.bean.vo.tweet.mapper.UserVoMapper;
import com.mgdiez.domain.bean.UserBo;
import com.twitter.sdk.android.core.models.User;

import net.TwitterApiService;

import io.realm.Realm;
import repository.RealmHelper;
import rx.Observable;

/**
 * Implementation of {@link UserDatastore} which represents the remote data stored in the cloud.
 */
public class CloudUserDatastore implements UserDatastore {

    private Context context;

    private TwitterApiService twitterApiService;

    public CloudUserDatastore(Context context) {
        this.context = context;
        twitterApiService = new TwitterApiService();
    }

    /**
     *
     * @return Observable User
     */
    @Override
    public Observable<UserBo> getUserData() {
        return twitterApiService.getUserData().doOnNext(new UserPersistAction()).map(UserDtoMapper::toBo);
    }

    /**
     * Action1 used to saved the User to database
     */
    private class UserPersistAction implements rx.functions.Action1<User> {
        @Override
        public void call(User user) {
            Realm realm = RealmHelper.getInstance(context);
            UserVo userVo = UserVoMapper.toVo(user);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(userVo);
            realm.commitTransaction();

            realm.close();
        }
    }
}
