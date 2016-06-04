package com.mgdiez.domain.bean;

import lombok.Data;

@Data
public class UserBo {
    private String userName;
    private String twitterImage;
    private String userPublicName;
    private String backgroundImage;
    private int followers;
    private int following;
    private int nTweets;
}

/*

    usernameTxt = user.screenName;
    String twitterImage = user.profileImageUrl.replace(TweetStatConstants.NORMAL_SIZE, "");
    String userpublicName = user.name;
    String backgroundImage = user.profileBannerUrl;
    int followers = user.followersCount;
    int following = user.friendsCount;
    int ntweets = user.statusesCount;
 */