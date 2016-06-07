package com.mgdiez.data.bean.vo.tweet;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class IntegerVo extends RealmObject {
    private int integer;

    public IntegerVo() {}

    public IntegerVo (int value) {
        integer = value;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }
}
