package com.mgdiez.tweetstat.presenter;

/**
 * Created by Marc on 7/6/16.
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
