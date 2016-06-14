package com.mgdiez.tweetstat.model;

import lombok.Data;

@Data
public class StatisticModel {

    int id;

    String dateGenerated;

    String type;

    String subType;

    String nTweets;

    String selectedOption;
}
