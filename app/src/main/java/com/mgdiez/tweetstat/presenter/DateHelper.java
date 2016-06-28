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

/**
 * Helper class that transform days in MMM pattern to natural language.
 */
public class DateHelper {

    public static String getDayToStatistics(String date){
        String day = date.substring(0,3);
        String dayStatistics = "";
        switch (day){
            case "Mon":
                dayStatistics =  "Monday";
                break;
            case "Tue":
                dayStatistics = "Tuesday";
                break;
            case "Wed":
                dayStatistics = "Wednesday";
                break;
            case "Thu":
                dayStatistics = "Thursday";
                break;
            case "Fri":
                dayStatistics = "Friday";
                break;
            case "Sat":
                dayStatistics = "Saturday";
                break;
            case "Sun":
                dayStatistics = "Sunday";
                break;
        }
        return dayStatistics;
    }
}
