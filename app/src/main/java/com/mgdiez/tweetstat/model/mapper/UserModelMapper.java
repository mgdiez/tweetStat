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
package com.mgdiez.tweetstat.model.mapper;

import com.mgdiez.domain.bean.UserBo;
import com.mgdiez.tweetstat.model.UserModel;

/**
 * ModelMapper class for User. Bo to Model
 */
public class UserModelMapper {

    public static UserModel toModel(UserBo bo){
        UserModel model = new UserModel();
        if (bo != null) {
            model.setUserName(bo.getUserName());
            model.setTwitterImage(bo.getTwitterImage());
            model.setUserPublicName(bo.getUserPublicName());
            model.setBackgroundImage(bo.getBackgroundImage());
            model.setFollowers(bo.getFollowers());
            model.setFollowing(bo.getFollowing());
            model.setNTweets(bo.getNTweets());
        }

        return model;
    }
}
