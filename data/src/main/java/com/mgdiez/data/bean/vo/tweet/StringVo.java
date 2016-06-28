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

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Value Object that represents a String in Database. Used for RealmList<String> until they give full
 * support to String native objects.
 */
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
