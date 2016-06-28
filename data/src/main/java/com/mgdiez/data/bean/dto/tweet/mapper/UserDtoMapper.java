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

import com.mgdiez.domain.bean.UserBo;
import com.twitter.sdk.android.core.models.User;

/**
 * DtoMapper class for User. Dto to Bo and Dto to Vo.
 */
public class UserDtoMapper {

    public static UserBo toBo(User dto){
        UserBo bo = new UserBo();
        if (dto != null) {
            bo.setUserName(dto.screenName);
            bo.setTwitterImage(dto.profileImageUrl.replace("_normal", ""));
            bo.setUserPublicName(dto.name);
            bo.setBackgroundImage(dto.profileBannerUrl + "/mobile");
            bo.setFollowers(dto.followersCount);
            bo.setFollowing(dto.friendsCount);
            bo.setNTweets(dto.statusesCount);
        }

        return bo;
    }
}
