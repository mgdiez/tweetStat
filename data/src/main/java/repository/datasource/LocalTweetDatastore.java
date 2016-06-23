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

import com.mgdiez.data.bean.vo.tweet.TweetVo;
import com.mgdiez.data.bean.vo.tweet.hashtag.HashtagVo;
import com.mgdiez.data.bean.vo.tweet.hashtag.HashtagVoMapper;
import com.mgdiez.data.bean.vo.tweet.mapper.TweetVoMapper;
import com.mgdiez.domain.bean.HashtagBo;
import com.mgdiez.domain.bean.TweetBo;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import repository.RealmHelper;
import rx.Observable;

/**
 * Implementation of {@link TweetDatastore} which represents the local data stored in the database.
 */
public class LocalTweetDatastore implements TweetDatastore {

    private Realm realm;

    public LocalTweetDatastore(Context context) {
        this.realm = RealmHelper.getInstance(context);
    }

    @Override
    public Observable<List<TweetBo>> getTweetsUsertimeline(String userName) {
            RealmResults<TweetVo> tweetVos = realm.where(TweetVo.class).equalTo(TweetVo.USERNAME, userName).findAll();
            List<TweetBo> tweetBos = TweetVoMapper.toBo(tweetVos);

            if (tweetBos != null && !tweetBos.isEmpty()) {
                return Observable.just(tweetBos);
            }

        return Observable.empty();
    }

    @Override
    public Observable<List<TweetBo>> getTweetsHometimeline(String userName) {
            RealmResults<TweetVo> tweetVos = realm.where(TweetVo.class).notEqualTo(TweetVo.USERNAME,userName).findAll();
            List<TweetBo> tweetBos = TweetVoMapper.toBo(tweetVos);

            if (tweetBos != null && !tweetBos.isEmpty()) {
                return Observable.just(tweetBos);
            }
        return Observable.empty();
    }

    @Override
    public Observable<List<HashtagBo>> getHashtags() {

        RealmResults<HashtagVo> hashtagVos = realm.where(HashtagVo.class).findAll();
        List<HashtagBo> hashtagBos = HashtagVoMapper.toBo(hashtagVos);

        if (hashtagBos != null && !hashtagBos.isEmpty()) {
            return Observable.just(hashtagBos);
        }

        return Observable.empty();
    }

    @Override
    public Observable<List<TweetBo>> getTweetsBySearch(String search) {
        RealmResults<TweetVo> tweetVos = realm.where(TweetVo.class).equalTo(TweetVo.QUERY, search).findAll();
        List<TweetBo> tweetBos = TweetVoMapper.toBo(tweetVos);

        if (tweetBos != null && !tweetBos.isEmpty()) {
            return Observable.just(tweetBos);
        }
        return Observable.empty();
    }
}
