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
package com.mgdiez.tweetstat.model.mapper;

import com.mgdiez.domain.bean.StatisticBo;
import com.mgdiez.tweetstat.model.StatisticModel;

import java.util.ArrayList;
import java.util.List;

/**
 * ModelMapper class for Statistics. Bo List to Model List
 */
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
                model.setSelectedOption(bo.getSelectedOption());

                statisticModels.add(model);
            }
        }
        return statisticModels;
    }
}
