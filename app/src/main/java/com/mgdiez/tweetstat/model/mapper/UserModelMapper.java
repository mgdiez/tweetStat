package com.mgdiez.tweetstat.model.mapper;

import com.mgdiez.domain.bean.UserBo;
import com.mgdiez.tweetstat.model.UserModel;

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
