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

import com.mgdiez.domain.bean.HashtagBo;
import com.mgdiez.tweetstat.model.HashtagModel;

import java.util.ArrayList;
import java.util.List;

/**
 * ModelMapper class for Hashtags (Trending Topics). Bo to Model
 */

public class HashtagModelMapper {
    public static HashtagModel toModel(HashtagBo bo) {
        HashtagModel model = null;

        if (bo != null) {
            model = new HashtagModel();

            model.setName(bo.getName());
            model.setNTweets(bo.getNTweets());
            model.setQueryName(bo.getQueryName());

        }

        return model;
    }

    public static List<HashtagModel> toModel(List<HashtagBo> bos) {
        List<HashtagModel> models = null;

        if (bos != null && !bos.isEmpty()) {
            models = new ArrayList<>(bos.size());

            for (HashtagBo bo : bos) {
                models.add(toModel(bo));
            }
        }

        return models;
    }
}
