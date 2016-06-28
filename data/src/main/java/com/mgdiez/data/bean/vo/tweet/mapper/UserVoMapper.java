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

import com.mgdiez.data.bean.vo.tweet.UserVo;
import com.mgdiez.domain.bean.UserBo;
import com.twitter.sdk.android.core.models.User;

/**
 * VoMapper used for User. Bo to Vo, Vo to Bo.
 */
public class UserVoMapper {

    public static UserBo toBo (UserVo vo){
        UserBo bo = new UserBo();
        if (vo != null) {
            bo.setUserName(vo.getUserName());
            bo.setTwitterImage(vo.getTwitterImage());
            bo.setUserPublicName(vo.getUserPublicName());
            bo.setBackgroundImage(vo.getBackgroundImage());
            bo.setFollowers(vo.getFollowers());
            bo.setFollowing(vo.getFollowing());
            bo.setNTweets(vo.getnTweets());
        }

        return bo;
    }

    public static UserVo toVo(User dto) {
        UserVo vo = new UserVo();
        if (dto != null) {
            vo.setUserName(dto.screenName);
            vo.setTwitterImage(dto.profileImageUrl.replace("_normal", ""));
            vo.setUserPublicName(dto.name);
            vo.setBackgroundImage(dto.profileBannerUrl + "/mobile");
            vo.setFollowers(dto.followersCount);
            vo.setFollowing(dto.friendsCount);
            vo.setnTweets(dto.statusesCount);
        }

        return vo;
    }
}
