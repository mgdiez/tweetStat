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
 * Interface for retrieving tweets data.
 */

public interface TweetsRepository {

    Observable<List<TweetBo>> getTweetsBySearch(String query, boolean refresh);
    Observable<List<TweetBo>> getTweetsByHashtag(String hashtag, boolean refresh);
    Observable<List<HashtagBo>> getHashtags(boolean refresh);
    Observable<List<TweetBo>> getTweetsHometimeline(String userName, boolean refresh);
    Observable<List<TweetBo>> getTweetsTimeline(boolean refresh, String userName);

}
