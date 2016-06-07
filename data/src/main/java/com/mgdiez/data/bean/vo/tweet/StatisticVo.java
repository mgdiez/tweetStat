package com.mgdiez.data.bean.vo.tweet;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class StatisticVo extends RealmObject {

    public static final String ID = "id";
    public static final String TYPE = "type";

    @PrimaryKey
    private int id;

    private RealmList<StringVo> stringValues;

    private RealmList<IntegerVo> integerValues;

    private int nTweets;

    private String type;

    private String subType;
    private String dateGenerated;

    public RealmList<StringVo> getStringValues() {
        return stringValues;
    }

    public void setStringValues(RealmList<StringVo> stringValues) {
        this.stringValues = stringValues;
    }

    public RealmList<IntegerVo> getIntegerValues() {
        return integerValues;
    }

    public void setIntegerValues(RealmList<IntegerVo> integerValues) {
        this.integerValues = integerValues;
    }

    public int getnTweets() {
        return nTweets;
    }

    public void setnTweets(int nTweets) {
        this.nTweets = nTweets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(String dateGenerated) {
        this.dateGenerated = dateGenerated;
    }
}
