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
package com.mgdiez.tweetstat.injector.component;

import android.app.Activity;

import com.mgdiez.tweetstat.injector.PerActivity;
import com.mgdiez.tweetstat.injector.module.ActivityModule;
import com.mgdiez.tweetstat.view.activity.TweetStattBaseActivity;

import dagger.Component;

/**
 * A base component.
 * Subtypes of ActivityComponent should be decorated with annotation: {@link PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(TweetStattBaseActivity activity);

    // Publicly available
    Activity getActivity();
}