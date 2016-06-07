package com.mgdiez.data.bean.vo.tweet;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class StringVo extends RealmObject {

    private String stringValue;

    public StringVo() {}

    public StringVo (String string) {
        this.stringValue = string;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
