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
package com.mgdiez.domain.repository;

import com.mgdiez.domain.bean.HashtagBo;
import com.mgdiez.domain.bean.TweetBo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Class that represents the Repository Pattern for Tweets Data
 */
public interface TweetsRepository {
    /**
     *
     * @param query the search introduced by the user
     * @param refresh true if force go to server or false if go to local first.
     * @return Observable Tweets List with the results of the query.
     */
    Observable<List<TweetBo>> getTweetsBySearch(String query, boolean refresh);
    /**
     *
     * @param hashtag to get the tweets related with.
     * @param refresh true if force go to server or false if go to local first.
     * @return Observable Tweets List with the results of the query.
     */
    Observable<List<TweetBo>> getTweetsByHashtag(String hashtag, boolean refresh);
    /**
     *
     * @param refresh true if force go to server or false if go to local first.
     * @return Observable Tweets List with the results of the query.
     */
    Observable<List<HashtagBo>> getHashtags(boolean refresh);
    /**
     *
     * @param userName name of the user.
     * @param refresh true if force go to server or false if go to local first.
     * @return Observable Tweets List with the results of the query.
     */
    Observable<List<TweetBo>> getTweetsHometimeline(String userName, boolean refresh);
    /**
     *
     * @param refresh true if force go to server or false if go to local first.
     * @param userName name of the user.
     * @return Observable Tweets List with the results of the query.
     */
    Observable<List<TweetBo>> getTweetsTimeline(boolean refresh, String userName);
}
