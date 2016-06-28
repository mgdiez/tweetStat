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
package com.mgdiez.data.bean.vo.tweet.mapper;

import com.mgdiez.data.bean.vo.tweet.IntegerVo;
import com.mgdiez.data.bean.vo.tweet.StatisticVo;
import com.mgdiez.data.bean.vo.tweet.StringVo;
import com.mgdiez.domain.bean.StatisticBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * VoMapper used for Statistics. Bo to Vo and Vo to Bo.
 */
public class StatisticVoMapper {

    public static StatisticVo toVo(StatisticBo bo) {

        StatisticVo vo = new StatisticVo();
        if (bo != null) {
            vo.setnTweets(bo.getNTweets());
            RealmList<StringVo> keys = new RealmList<>();
            RealmList<IntegerVo> values = new RealmList<>();

            Iterator it = bo.getStatisticsData().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                keys.add(new StringVo((String)e.getKey()));
                values.add(new IntegerVo((Integer)e.getValue()));
            }

            vo.setIntegerValues(values);
            vo.setStringValues(keys);
            vo.setSubType(bo.getSubType());
            vo.setType(bo.getType());
            vo.setDateGenerated(bo.getDateGenerated());
            vo.setSelectedOption(bo.getSelectedOption());
        }

        return vo;
    }

    public static StatisticBo toBo(StatisticVo vo){

        StatisticBo bo = new StatisticBo();
        if (vo != null) {
            bo.setNTweets(vo.getnTweets());
            HashMap<String, Integer> data = new HashMap<>();
            RealmList<StringVo> keys = vo.getStringValues();
            RealmList<IntegerVo> values = vo.getIntegerValues();

            for (int i = 0; i < keys.size(); i++) {
                data.put(keys.get(i).getStringValue(), values.get(i).getInteger());
            }
            bo.setStatisticsData(data);
            bo.setId(vo.getId());
            bo.setType(vo.getType());
            bo.setSubType(vo.getSubType());
            bo.setDateGenerated(vo.getDateGenerated());
            bo.setSelectedOption(vo.getSelectedOption());
        }
        return bo;
    }

    public static List<StatisticBo> toBo (RealmResults<StatisticVo> vos){
        List<StatisticBo> bos = new ArrayList<>();
        for (StatisticVo vo : vos){
            bos.add(toBo(vo));
        }
        return bos;
    }
}
