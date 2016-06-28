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
package com.mgdiez.data.bean.dto.tweet.mapper;

import com.mgdiez.data.bean.dto.TrendsList;
import com.mgdiez.domain.bean.HashtagBo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DtoMapper class for Hashtags (Trending topics). Dto to Bo.
 */
public class HashtagDtoMapper {

    public static List<HashtagBo> toBo(Collection<TrendsList> trendsLists){
        ArrayList<HashtagBo> hashtagBos = new ArrayList<>();

        if (trendsLists != null && !trendsLists.isEmpty()) {
            for (TrendsList trend : trendsLists) {
                HashtagBo bo = new HashtagBo();
                bo.setName(trend.name);
                try {
                    bo.setNTweets(trend.nTweets);
                }catch (Exception e){
                    bo.setNTweets(-1);
                }
                bo.setQueryName(trend.queryString);
                hashtagBos.add(bo);
            }
        }

        return hashtagBos;
    }
}
