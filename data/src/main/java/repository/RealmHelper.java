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
package repository;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {

    /**
     *
     * @param context needed by Realm Builder.
     * @return the instance of Realm ready to be used.
     */
    public static Realm getInstance(Context context){
        RealmConfiguration config = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();

        return Realm.getInstance(config);
    }
}
