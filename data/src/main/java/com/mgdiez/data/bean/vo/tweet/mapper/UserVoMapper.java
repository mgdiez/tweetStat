package com.mgdiez.data.bean.vo.tweet.mapper;

import com.mgdiez.data.bean.vo.tweet.UserVo;
import com.mgdiez.domain.bean.UserBo;

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
}
