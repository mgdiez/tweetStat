package com.mgdiez.tweetstat.model.mapper;


import com.mgdiez.domain.bean.StatisticBo;
import com.mgdiez.tweetstat.model.StatisticModel;

import java.util.ArrayList;
import java.util.List;

public class StatisticModelMapper {


    public static List<StatisticModel> toModel(List<StatisticBo> statisticBo) {
        List<StatisticModel> statisticModels = new ArrayList<>();

        if (statisticBo != null && !statisticBo.isEmpty()) {
            for (StatisticBo bo : statisticBo) {
                StatisticModel model = new StatisticModel();
                model.setId(bo.getId());
                model.setType(bo.getType());
                model.setSubType(bo.getSubType());
                model.setDateGenerated(bo.getDateGenerated());
                model.setNTweets(String.valueOf(bo.getNTweets()));

                statisticModels.add(model);
            }
        }
        return statisticModels;
    }
}
