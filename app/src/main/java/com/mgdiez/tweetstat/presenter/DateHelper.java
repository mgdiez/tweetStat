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
package com.mgdiez.tweetstat.presenter;

import android.content.Context;

import com.mgdiez.tweetstat.R;

/**
 * Helper class that transform days in MMM pattern to natural language.
 */
public class DateHelper {

    public static String getDayToStatistics(String date, Context context) {
        String day = date.substring(0, 3);
        String dayStatistics = "";
        switch (day) {
            case "Mon":
                dayStatistics = context.getString(R.string.monday);
                break;
            case "Tue":
                dayStatistics = context.getString(R.string.tuesday);
                break;
            case "Wed":
                dayStatistics = context.getString(R.string.wednesday);
                break;
            case "Thu":
                dayStatistics = context.getString(R.string.thursday);
                break;
            case "Fri":
                dayStatistics = context.getString(R.string.friday);
                break;
            case "Sat":
                dayStatistics = context.getString(R.string.saturday);
                break;
            case "Sun":
                dayStatistics = context.getString(R.string.sunday);
                break;
        }
        return dayStatistics;
    }
}
