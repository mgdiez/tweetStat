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
import android.content.SharedPreferences;

import com.mgdiez.data.R;
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
import repository.RealmHelper;
import rx.Observable;
import rx.functions.Action1;

/**
 * Implementation of {@link TweetDatastore} which represents the remote data stored in the cloud.
 */
public class CloudTweetDatastore implements TweetDatastore {

    private String USER_TIMELINE = "user_timeline_sp";

    private String HOME_TIMELINE = "home_timeline_sp";

    private Context context;

    private TwitterApiService twitterApiService;

    public CloudTweetDatastore(Context context) {
        this.context = context;
        twitterApiService = new TwitterApiService();
    }

    /**
     *
     * @param username name of the user
     * @return Observable List of Tweets
     */
    @Override
    public Observable<List<TweetBo>> getTweetsUsertimeline(String username){
        return twitterApiService.getTweetsUsertimeline(username,
                getLastModificationTweet(USER_TIMELINE)) //ask for the tweets
                .doOnNext(new TweetsActionPersist(USER_TIMELINE)) //save to database
                .map(TweetsDtoMapper::toBo); //map from Dto to Bo
    }

    /**
     *
     * @param userName name of the user
     * @return Observable List of Tweets
     */
    @Override
    public Observable<List<TweetBo>> getTweetsHometimeline(String userName) {
        return twitterApiService.getTweetsHometimeline(getLastModificationTweet(HOME_TIMELINE)) //ask for the tweets
                .doOnNext(new TweetsActionPersist(HOME_TIMELINE)) //save to database
                .map(TweetsDtoMapper::toBo); //map from Dto to Bo
    }

    /**
     *
     * @return Observable List of Hashtags with Treding topics atm.
     */
    @Override
    public Observable<List<HashtagBo>> getHashtags() {
        return twitterApiService.getHashtags() //ask for the trending topics
                .doOnNext(new HashtagActionPersist()) //save to database
                .map(HashtagDtoMapper::toBo); //map from Dto to Bo
    }

    /**
     *
     * @param search query to search
     * @return Observable List of Tweets with the results.
     */
    @Override
    public Observable<List<TweetBo>> getTweetsBySearch(String search) {
        return twitterApiService.getTweetsBySearch(search) //ask for the tweets
                .doOnNext(new TweetsSearchActionPersist(search)) //save to database
                .map(TweetsDtoMapper::toBo); //map from Dto to Bo.
    }

    /**
     * Action1 to persist a List of Tweets in Database
     */
    private class TweetsActionPersist implements Action1<List<Tweet>> {
        private String type;

        public TweetsActionPersist(String type) {
            this.type = type;
        }

        @Override
        public void call(List<Tweet> tweets) {
            if (tweets != null && !tweets.isEmpty()) {
                saveLastModificationTweet(type, tweets.get(0).getId()); //Save the last tweet ID.
                Realm realm = RealmHelper.getInstance(context); //Get the Realm instance.
                List<TweetVo> tweetVos = TweetVoMapper.toVo(tweets); //Map from Dto to Vo.

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(tweetVos); //save
                realm.commitTransaction();

                realm.close();
            }
        }
    }

    /**
     * Same as TweetsActionPersist but needs the query introduced by the user to save the tweets from
     * a search.
     */
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

    /**
     * Action1 used to save a list of hasthags to database
     */
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

    /**
     *
     * @param type of the tweet (timeline or own tweets)
     * @return id that representes the last tweet we have in database.
     */
    private Long getLastModificationTweet(String type) {
        Long lastId;
        lastId = context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE).getLong(type, -1);
        if (lastId == -1){
            lastId = null;
        }

        return lastId;
    }

    /**
     *
     * @param type of the tweet (timeline or own tweets)
     * @param id to be saved
     */
    private void saveLastModificationTweet(String type, Long id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
        editor.putLong(type, id);
        editor.apply();
    }
}
