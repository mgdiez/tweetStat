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
package repository.datasource;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.twitter.sdk.android.core.TwitterSession;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.SerializationStrategy;

public class SerializeTwitterSession implements SerializationStrategy {
    private final Gson gson = new Gson();

    public SerializeTwitterSession() {
    }

    public String serialize(Object session) {
        TwitterSession twitterSession = (TwitterSession) session;
        if(twitterSession != null && twitterSession.getAuthToken() != null) {
            try {
                return this.gson.toJson(twitterSession);
            } catch (Exception var3) {
                Fabric.getLogger().d("Twitter", var3.getMessage());
            }
        }

        return "";
    }


    public TwitterSession deserialize(String serializedSession) {
        if(!TextUtils.isEmpty(serializedSession)) {
            try {
                return (TwitterSession)this.gson.fromJson(serializedSession, TwitterSession.class);
            } catch (Exception var3) {
                Fabric.getLogger().d("Twitter", var3.getMessage());
            }
        }

        return null;
    }
}
