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
package com.mgdiez.data.bean.vo.tweet.hashtag;


import com.mgdiez.data.bean.dto.TrendsList;
import com.mgdiez.domain.bean.HashtagBo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.RealmResults;

/**
 * VoMapper used for Hashtags. Dto to Vo, Vo to Bo.
 */
public class HashtagVoMapper {

    public static List<HashtagVo> toVo(Collection<TrendsList> dtos) {
        ArrayList<HashtagVo> vos = new ArrayList<>();

        if (dtos != null && !dtos.isEmpty()) {
            for (TrendsList trend : dtos) {
                vos.add(toVo(trend));
            }
        }

        return vos;
    }

    public static HashtagVo toVo(TrendsList dto) {
        HashtagVo vo = new HashtagVo();
        if (dto != null) {
            vo.setName(dto.name);
            vo.setQueryName(dto.queryString);
            try {
                vo.setnTweets(dto.nTweets);
            }catch (Exception e){
                vo.setnTweets(-1);
            }
        }
        return vo;
    }

    public static List<HashtagBo> toBo(RealmResults<HashtagVo> vos){
        ArrayList<HashtagBo> bos = new ArrayList<>();
        if (vos != null && !vos.isEmpty()){
            for(HashtagVo vo : vos){
                bos.add(toBo(vo));
            }
        }

        return bos;
    }

    public static HashtagBo toBo (HashtagVo vo) {
        HashtagBo bo = new HashtagBo();
        if (vo != null){
            bo.setName(vo.getName());
            bo.setQueryName(vo.getQueryName());
            try {
                bo.setNTweets(vo.getnTweets());
            } catch (Exception e){
                bo.setNTweets(-1);
            }
        }

        return bo;
    }
}
