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
import com.twitter.sdk.android.core.models.Coordinates;
import com.twitter.sdk.android.core.models.Identifiable;
import com.twitter.sdk.android.core.models.Place;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetEntities;
import com.twitter.sdk.android.core.models.User;

import java.util.Collection;
import java.util.List;

public class HashtagDto implements Identifiable{
    public static final long INVALID_ID = -1L;
    @SerializedName("trends")
    public final Collection<TrendsList> trendsList;
    @SerializedName("coordinates")
    public final Coordinates coordinates;
    @SerializedName("created_at")
    public final String createdAt;
    @SerializedName("current_user_retweet")
    public final Object currentUserRetweet;
    @SerializedName("entities")
    public final TweetEntities entities;
    @SerializedName("extended_entities")
    public final TweetEntities extendedEtities;
    @SerializedName("favorite_count")
    public final Integer favoriteCount;
    @SerializedName("favorited")
    public final boolean favorited;
    @SerializedName("filter_level")
    public final String filterLevel;
    @SerializedName("id")
    public final long id;
    @SerializedName("id_str")
    public final String idStr;
    @SerializedName("in_reply_to_screen_name")
    public final String inReplyToScreenName;
    @SerializedName("in_reply_to_status_id")
    public final long inReplyToStatusId;
    @SerializedName("in_reply_to_status_id_str")
    public final String inReplyToStatusIdStr;
    @SerializedName("in_reply_to_user_id")
    public final long inReplyToUserId;
    @SerializedName("in_reply_to_user_id_str")
    public final String inReplyToUserIdStr;
    @SerializedName("lang")
    public final String lang;
    @SerializedName("place")
    public final Place place;
    @SerializedName("possibly_sensitive")
    public final boolean possiblySensitive;
    @SerializedName("scopes")
    public final Object scopes;
    @SerializedName("retweet_count")
    public final int retweetCount;
    @SerializedName("retweeted")
    public final boolean retweeted;
    @SerializedName("retweeted_status")
    public final Tweet retweetedStatus;
    @SerializedName("source")
    public final String source;
    @SerializedName("text")
    public final String text;
    @SerializedName("truncated")
    public final boolean truncated;
    @SerializedName("user")
    public final User user;
    @SerializedName("withheld_copyright")
    public final boolean withheldCopyright;
    @SerializedName("withheld_in_countries")
    public final List<String> withheldInCountries;
    @SerializedName("withheld_scope")
    public final String withheldScope;


    /** @deprecated */
    @Deprecated
    public HashtagDto(Collection<TrendsList> trendsList, Coordinates coordinates, String createdAt, Object currentUserRetweet, TweetEntities entities, Integer favoriteCount, boolean favorited, String filterLevel, long id, String idStr, String inReplyToScreenName, long inReplyToStatusId, String inReplyToStatusIdStr, long inReplyToUserId, String inReplyToUserIdStr, String lang, Place place, boolean possiblySensitive, Object scopes, int retweetCount, boolean retweeted, Tweet retweetedStatus, String source, String text, boolean truncated, User user, boolean withheldCopyright, List<String> withheldInCountries, String withheldScope) {
        this(trendsList, coordinates, createdAt, currentUserRetweet, entities, (TweetEntities)null, favoriteCount, favorited, filterLevel, id, idStr, inReplyToScreenName, inReplyToStatusId, inReplyToStatusIdStr, inReplyToUserId, inReplyToUserIdStr, lang, place, possiblySensitive, scopes, retweetCount, retweeted, retweetedStatus, source, text, truncated, user, withheldCopyright, withheldInCountries, withheldScope);
    }


    public HashtagDto(Collection<TrendsList> trendsList, Coordinates coordinates, String createdAt, Object currentUserRetweet, TweetEntities entities, TweetEntities extendedEtities, Integer favoriteCount, boolean favorited, String filterLevel, long id, String idStr, String inReplyToScreenName, long inReplyToStatusId, String inReplyToStatusIdStr, long inReplyToUserId, String inReplyToUserIdStr, String lang, Place place, boolean possiblySensitive, Object scopes, int retweetCount, boolean retweeted, Tweet retweetedStatus, String source, String text, boolean truncated, User user, boolean withheldCopyright, List<String> withheldInCountries, String withheldScope) {
        this.trendsList = trendsList;
        this.coordinates = coordinates;
        this.createdAt = createdAt;
        this.currentUserRetweet = currentUserRetweet;
        this.entities = entities;
        this.extendedEtities = extendedEtities;
        this.favoriteCount = favoriteCount;
        this.favorited = favorited;
        this.filterLevel = filterLevel;
        this.id = id;
        this.idStr = idStr;
        this.inReplyToScreenName = inReplyToScreenName;
        this.inReplyToStatusId = inReplyToStatusId;
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
        this.inReplyToUserId = inReplyToUserId;
        this.inReplyToUserIdStr = inReplyToUserIdStr;
        this.lang = lang;
        this.place = place;
        this.possiblySensitive = possiblySensitive;
        this.scopes = scopes;
        this.retweetCount = retweetCount;
        this.retweeted = retweeted;
        this.retweetedStatus = retweetedStatus;
        this.source = source;
        this.text = text;
        this.truncated = truncated;
        this.user = user;
        this.withheldCopyright = withheldCopyright;
        this.withheldInCountries = withheldInCountries;
        this.withheldScope = withheldScope;
    }

    @Override
    public long getId() {
        return 0;
    }

    public boolean equals(Object o) {
        if(o == null) {
            return false;
        } else if(!(o instanceof HashtagDto)) {
            return false;
        } else {
            HashtagDto other = (HashtagDto)o;
            return this.id == other.id;
        }
    }

    public int hashCode() {
        return (int)this.id;
    }
}
