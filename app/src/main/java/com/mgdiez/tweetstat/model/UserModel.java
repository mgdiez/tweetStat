package com.mgdiez.tweetstat.model;

import lombok.Data;

@Data
public class UserModel {
    private String userName;
    private String twitterImage;
    private String userPublicName;
    private String backgroundImage;
    private int followers;
    private int following;
    private int nTweets;
}
