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
package net;

import com.mgdiez.data.bean.dto.HashtagDto;
import com.mgdiez.data.bean.dto.TrendsList;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.Collection;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public class TwitterApiService extends TwitterApiClient {

    private TwitterApiClient twitterApiClient;

    public TwitterApiService() {
        super(Twitter.getSessionManager().getActiveSession());
        Session session = Twitter.getSessionManager().getActiveSession();
        twitterApiClient = TwitterCore.getInstance().getApiClient(session);


    }

    public Observable<List<Tweet>> getTweetsHometimeline() {
        return Observable.create(subscriber -> twitterApiClient.getStatusesService().homeTimeline(200, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        subscriber.onNext(result.data);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void failure(TwitterException e) {
                        subscriber.onError(e);
                    }
                }

        ));
    }


    public Observable<List<Tweet>> getTweetsUsertimeline(String username) {
        return Observable.create(subscriber ->
                twitterApiClient.getStatusesService()
                        .userTimeline(null,
                                username,
                                3000,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                new com.twitter.sdk.android.core.Callback<List<Tweet>>() {
                                    @Override
                                    public void success(Result<List<Tweet>> result) {
                                        subscriber.onNext(result.data);
                                        subscriber.onCompleted();
                                    }

                                    @Override
                                    public void failure(TwitterException exception) {
                                        subscriber.onError(exception);
                                    }
                                })
        );
    }

    public Observable<List<Tweet>> getTweetsBySearch(String search) {
        return Observable.create(subscriber -> {
            twitterApiClient.getSearchService().tweets(search, null, null, null, null, 1000, null, null, null, null, new Callback<Search>() {
                @Override
                public void success(Result<Search> result) {
                    subscriber.onNext(result.data.tweets);
                    subscriber.onCompleted();
                }

                @Override
                public void failure(TwitterException e) {
                    subscriber.onError(e);
                }
            });
        });
    }

    public Observable<Collection<TrendsList>> getHashtags() {
        return Observable.create(subscriber -> getHashtagsApiService().getHashtags("23424950", new Callback<List<HashtagDto>>() {
            @Override
            public void success(Result<List<HashtagDto>> result) {
                if (result.data.size() > 0 && !result.data.get(0).trendsList.isEmpty()) {
                    Collection<TrendsList> trendsList = result.data.get(0).trendsList;
                    subscriber.onNext(trendsList);
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(null);
                }

            }

            @Override
            public void failure(TwitterException e) {
                subscriber.onError(e);
            }
        }));
    }

    public HashtagsApiService getHashtagsApiService() {
        return getService(HashtagsApiService.class);
    }

    interface HashtagsApiService {
        @GET("/1.1/trends/place.json")
        void getHashtags(@Query("id") String id, Callback<List<HashtagDto>> cb);
    }
}
