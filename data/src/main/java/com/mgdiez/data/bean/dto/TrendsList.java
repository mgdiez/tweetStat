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
package com.mgdiez.data.bean.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Object that represents a trending topic in Twitter API.
 */
public class TrendsList {
    @SerializedName("tweet_volume")
    public final Integer nTweets;
    @SerializedName("events")
    public final String events;
    @SerializedName("name")
    public final String name;
    @SerializedName("promoted_content")
    public final String promotedContent;
    @SerializedName("query")
    public final String queryString;
    @SerializedName("url")
    public final String url;

    public TrendsList(int nTweets, String events, String name, String promotedContent, String queryString, String url) {
        this.nTweets = nTweets;
        this.events = events;
        this.name = name;
        this.promotedContent = promotedContent;
        this.queryString = queryString;
        this.url = url;
    }
}
