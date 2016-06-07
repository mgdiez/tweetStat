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
