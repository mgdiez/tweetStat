package com.mgdiez.domain.bean;

import java.util.HashMap;

import lombok.Data;

@Data
public class StatisticBo {

    private int id;

    private HashMap<String, Integer> statisticsData;

    private int nTweets;

    private String type;

    private String subType;

    private String dateGenerated;

    private String selectedOption;

}
