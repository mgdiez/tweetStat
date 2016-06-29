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
package com.mgdiez.tweetstat.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mgdiez.tweetstat.TweetStattApplication;
import com.mgdiez.tweetstat.injector.component.ActivityComponent;
import com.mgdiez.tweetstat.injector.component.ApplicationComponent;
import com.mgdiez.tweetstat.injector.component.DaggerActivityComponent;
import com.mgdiez.tweetstat.injector.module.ActivityModule;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class TweetStattBaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
        activityComponent.inject(this);
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    public ApplicationComponent getApplicationComponent() {
        return ((TweetStattApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    private void initializeInjector() {
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
