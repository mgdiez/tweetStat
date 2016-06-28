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

import com.mgdiez.data.bean.vo.tweet.UserVo;
import com.mgdiez.data.bean.vo.tweet.mapper.UserVoMapper;
import com.mgdiez.domain.bean.UserBo;

import io.realm.Realm;
import repository.RealmHelper;
import rx.Observable;

/**
 * Implementation of {@link UserDatastore} which represents the local data stored in the database.
 */
public class LocalUserDatastore implements UserDatastore {

    private Realm realm;

    public LocalUserDatastore(Context context) {
        this.realm = RealmHelper.getInstance(context);
    }

    /**
     *
     * @return Observable of User
     */
    @Override
    public Observable<UserBo> getUserData() {
        UserVo userVo = realm.where(UserVo.class) //User Type
                .findFirst(); // Only one result will be aviable

        if (userVo != null) {
            return Observable.just(UserVoMapper.toBo(userVo));
        }
        return Observable.empty();
    }
}
