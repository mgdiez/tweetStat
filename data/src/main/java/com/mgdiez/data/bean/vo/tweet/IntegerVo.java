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
 * Value Object that represents an integer in Database. Used for RealmList<int> until they give full
 * support to integer native objects.
 */
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
