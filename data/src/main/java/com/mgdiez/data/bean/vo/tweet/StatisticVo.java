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
package com.mgdiez.data.bean.vo.tweet;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Value Object that represents a Statistic in Database.
 */
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

    private String selectedOption;

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

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
