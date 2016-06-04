package com.mgdiez.data.bean.dto.tweet.mapper;

import com.mgdiez.data.bean.vo.tweet.UserVo;
import com.mgdiez.domain.bean.UserBo;
import com.twitter.sdk.android.core.models.User;

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
