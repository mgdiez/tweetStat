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
package repository;

import android.content.Context;


import com.mgdiez.domain.bean.HashtagBo;
import com.mgdiez.domain.bean.TweetBo;
import com.mgdiez.domain.repository.TweetsRepository;

import java.util.List;

import repository.datasource.CloudTweetDatastore;
import repository.datasource.LocalTweetDatastore;
import rx.Observable;


/**
 * Implementation of {@link com.mgdiez.domain.repository.TweetsRepository} interface for retrieving comics data.
 */
public class TweetsRepositoryImpl implements TweetsRepository {

    private final Context context;

    public TweetsRepositoryImpl(Context context) {
        this.context = context;
    }


    @Override
    public Observable<List<TweetBo>> getTweetsBySearch(String query, boolean refresh) {
        CloudTweetDatastore cloudTweetDatastore = new CloudTweetDatastore(context);

        if (NetworkUtil.isNetworkAvailable(context) && refresh) {
            return cloudTweetDatastore.getTweetsBySearch(query);
        } else {
            return Observable.concat(new LocalTweetDatastore(context).getTweetsBySearch(query),
                    cloudTweetDatastore.getTweetsBySearch(query)
                    ).first();
        }

    }

    @Override
    public Observable<List<TweetBo>> getTweetsByHashtag(String hashtag, boolean refresh) {
        CloudTweetDatastore cloudTweetDatastore = new CloudTweetDatastore(context);

        if (NetworkUtil.isNetworkAvailable(context) && refresh) {
            return cloudTweetDatastore.getTweetsBySearch(hashtag);
        } else {
            return Observable.concat(new LocalTweetDatastore(context).getTweetsBySearch(hashtag),
                    cloudTweetDatastore.getTweetsBySearch(hashtag)
                    ).first();
        }
    }

    @Override
    public Observable<List<HashtagBo>> getHashtags(boolean refresh) {
        CloudTweetDatastore cloudTweetDatastore = new CloudTweetDatastore(context);

        if (NetworkUtil.isNetworkAvailable(context) && refresh) {
            return cloudTweetDatastore.getHashtags();
        } else {
            return Observable.concat(new LocalTweetDatastore(context).getHashtags(), cloudTweetDatastore.getHashtags()
                    ).first();
        }
    }

    @Override
    public Observable<List<TweetBo>> getTweetsHometimeline(boolean refresh) {
        CloudTweetDatastore cloudTweetDatastore = new CloudTweetDatastore(context);
        if (NetworkUtil.isNetworkAvailable(context) && refresh) {
            return cloudTweetDatastore.getTweetsHometimeline();
        } else {
            return Observable.concat(new LocalTweetDatastore(context).getTweetsHometimeline(), cloudTweetDatastore.getTweetsHometimeline()
                    ).first();
        }
    }

    @Override
    public Observable<List<TweetBo>> getTweetsTimeline(boolean refresh, String userName) {
        CloudTweetDatastore cloudTweetDatastore = new CloudTweetDatastore(context);
        if (NetworkUtil.isNetworkAvailable(context) && refresh) {
            return cloudTweetDatastore.getTweetsUsertimeline(userName);
        } else {
            return Observable.concat(new LocalTweetDatastore(context).getTweetsUsertimeline(userName),
                    cloudTweetDatastore.getTweetsUsertimeline(userName)).first();
        }
    }

}
