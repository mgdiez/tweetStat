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
package com.mgdiez.data.bean.dto.tweet.mapper;

import com.mgdiez.domain.bean.TweetBo;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsDtoMapper {

    public static ArrayList<TweetBo> toBo (List<Tweet> tweets){
        ArrayList<TweetBo> arrayList = new ArrayList<>();
        for (Tweet tweet : tweets) {
            arrayList.add(toBo(tweet));
        }
        return arrayList;
    }

    public static TweetBo toBo (Tweet tweet) {
        TweetBo tweetBo = new TweetBo();
        tweetBo.setId(tweet.getId());
        tweetBo.setTitle(tweet.text);
        tweetBo.setDescription(tweet.text);
        tweetBo.setThumbnailUrl(tweet.user.profileImageUrl);
        tweetBo.setCreatedAt(tweet.createdAt);
        if (tweet.place != null) {
            tweetBo.setCity(tweet.place.name);
            tweetBo.setCountry(tweet.place.country);
            tweetBo.setLocation(tweet.place.countryCode);
        }
        if (tweet.coordinates != null) {
            tweetBo.setLatitude(tweet.coordinates.getLatitude());
            tweetBo.setLongitude(tweet.coordinates.getLongitude());
        }
        return tweetBo;
    }
}
