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
package com.mgdiez.data.bean.vo.tweet.mapper;


import com.mgdiez.data.bean.vo.tweet.TweetVo;
import com.mgdiez.domain.bean.TweetBo;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * VoMapper used for Tweets. Bo to Vo, Vo to Bo and Dto to Vo.
 */
public final class TweetVoMapper {

    public static TweetBo toBo(TweetVo vo) {
        TweetBo bo = null;

        if (vo != null) {
            bo = new TweetBo();

            bo.setId(vo.getId());
            bo.setTitle(vo.getTitle());
            bo.setDescription(vo.getDescription());
            bo.setThumbnailUrl(vo.getThumbnailUrl());
            bo.setCreatedAt(vo.getCreatedAt());
            try {
                bo.setCity(vo.getCity());
                bo.setCountry(vo.getCountry());
                bo.setLocation(vo.getLocation());
                bo.setLatitude(vo.getLatitude());
                bo.setLongitude(vo.getLongitude());
            } catch (Exception e){}


        }

        return bo;
    }

    public static List<TweetBo> toBo(RealmResults<TweetVo> vos) {
        List<TweetBo> bos = null;

        if (vos != null && !vos.isEmpty()) {
            bos = new ArrayList<>();

            for (TweetVo vo : vos) {
                bos.add(toBo(vo));
            }
        }

        return bos;
    }

    public static RealmList<TweetVo> toVo (List<Tweet> tweets) {
        RealmList<TweetVo> tweetVos = new RealmList<>();
        for (Tweet tweet : tweets) {
            tweetVos.add(toVo(tweet));
        }

        return tweetVos;
    }

    public static RealmList<TweetVo> toVo (List<Tweet> tweets, String query) {
        RealmList<TweetVo> tweetVos = new RealmList<>();
        for (Tweet tweet : tweets) {
            TweetVo vo = toVo(tweet);
            vo.setQuery(query);
            tweetVos.add(vo);
        }

        return tweetVos;
    }

    public static TweetVo toVo (Tweet tweet) {
        TweetVo tweetVo = new TweetVo();
        tweetVo.setId(tweet.getId());
        tweetVo.setDescription(tweet.text);
        tweetVo.setThumbnailUrl(tweet.user.profileImageUrl);
        tweetVo.setUserName(tweet.user.screenName);
        tweetVo.setCreatedAt(tweet.createdAt);
        if (tweet.place != null) {
            tweetVo.setCity(tweet.place.name);
            tweetVo.setCountry(tweet.place.country);
            tweetVo.setLocation(tweet.place.countryCode);
        }
        if (tweet.coordinates != null) {
            tweetVo.setLatitude(tweet.coordinates.getLatitude());
            tweetVo.setLongitude(tweet.coordinates.getLongitude());
        }

        return tweetVo;
    }
}
