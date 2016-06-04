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

import com.mgdiez.domain.bean.TweetBo;
import com.mgdiez.tweetstat.model.TweetModel;

import java.util.ArrayList;
import java.util.List;

public class TweetModelMapper {

    public static TweetModel toModel(TweetBo bo) {
        TweetModel model = null;

        if (bo != null) {
            model = new TweetModel();

            model.setId(bo.getId());
            model.setTweet(bo.getDescription());
            model.setThumbnailUrl(bo.getThumbnailUrl().replace("_normal", ""));

        }

        return model;
    }

    public static List<TweetModel> toModel(List<TweetBo> bos) {
        List<TweetModel> models = null;

        if (bos != null && !bos.isEmpty()) {
            models = new ArrayList<>(bos.size());

            for (TweetBo bo : bos) {
                models.add(toModel(bo));
            }
        }

        return models;
    }
}
