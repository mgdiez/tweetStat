package com.mgdiez.data.bean.vo.tweet;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class UserVo extends RealmObject {

    @PrimaryKey
    private String userName;
    private String twitterImage;
    private String userPublicName;
    private String backgroundImage;
    private int followers;
    private int following;
    private int nTweets;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTwitterImage() {
        return twitterImage;
    }

    public void setTwitterImage(String twitterImage) {
        this.twitterImage = twitterImage;
    }

    public String getUserPublicName() {
        return userPublicName;
    }

    public void setUserPublicName(String userPublicName) {
        this.userPublicName = userPublicName;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getnTweets() {
        return nTweets;
    }

    public void setnTweets(int nTweets) {
        this.nTweets = nTweets;
    }
}
