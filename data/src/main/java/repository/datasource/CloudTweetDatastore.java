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

import com.mgdiez.data.bean.dto.TrendsList;
import com.mgdiez.data.bean.dto.tweet.mapper.HashtagDtoMapper;
import com.mgdiez.data.bean.dto.tweet.mapper.TweetsDtoMapper;
import com.mgdiez.data.bean.vo.tweet.TweetVo;
import com.mgdiez.data.bean.vo.tweet.hashtag.HashtagVo;
import com.mgdiez.data.bean.vo.tweet.hashtag.HashtagVoMapper;
import com.mgdiez.data.bean.vo.tweet.mapper.TweetVoMapper;
import com.mgdiez.domain.bean.HashtagBo;
import com.mgdiez.domain.bean.TweetBo;
import com.twitter.sdk.android.core.models.Tweet;

import net.TwitterApiService;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import okhttp3.logging.HttpLoggingInterceptor;
import repository.RealmHelper;
import rx.Observable;
import rx.functions.Action1;

/**
 * Implementation of {@link TweetDatastore} which represents the remote data stored in the cloud.
 */
public class CloudTweetDatastore implements TweetDatastore {

    private Context context;
    private TwitterApiService twitterApiService;

    public CloudTweetDatastore(Context context) {
        this.context = context;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        twitterApiService = new TwitterApiService();
    }

    @Override
    public Observable<List<TweetBo>> getTweetsUsertimeline(String username){
        return twitterApiService.getTweetsUsertimeline(username).doOnNext(new TweetsActionPersist()).map(TweetsDtoMapper::toBo);
    }

    @Override
    public Observable<List<TweetBo>> getTweetsHometimeline(String userName) {
        return twitterApiService.getTweetsHometimeline().doOnNext(new TweetsActionPersist()).map(TweetsDtoMapper::toBo);
    }

    @Override
    public Observable<List<HashtagBo>> getHashtags() {
        return twitterApiService.getHashtags().doOnNext(new HashtagActionPersist()).map(HashtagDtoMapper::toBo);
    }

    @Override
    public Observable<List<TweetBo>> getTweetsBySearch(String search) {
        return twitterApiService.getTweetsBySearch(search).doOnNext(new TweetsSearchActionPersist(search)).map(TweetsDtoMapper::toBo);
    }


    /** Persist actions **/
    private class TweetsActionPersist implements Action1<List<Tweet>> {

        @Override
        public void call(List<Tweet> tweets) {
            Realm realm = RealmHelper.getInstance(context);
            List<TweetVo> tweetVos = TweetVoMapper.toVo(tweets);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(tweetVos);
            realm.commitTransaction();

            realm.close();
        }
    }

    private class TweetsSearchActionPersist implements Action1<List<Tweet>> {
        private String query;

        public TweetsSearchActionPersist(String query) {
            this.query = query;
        }

        @Override
        public void call(List<Tweet> tweets) {
            Realm realm = RealmHelper.getInstance(context);
            List<TweetVo> tweetVos = TweetVoMapper.toVo(tweets, query);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(tweetVos);
            realm.commitTransaction();

            realm.close();
        }
    }

    private class HashtagActionPersist implements Action1<Collection<TrendsList>> {

        @Override
        public void call(Collection<TrendsList> hashtagDtos) {
            Realm realm = RealmHelper.getInstance(context);
            List<HashtagVo> hashtagVos = HashtagVoMapper.toVo(hashtagDtos);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(hashtagVos);
            realm.commitTransaction();

            realm.close();
        }
    }
}
