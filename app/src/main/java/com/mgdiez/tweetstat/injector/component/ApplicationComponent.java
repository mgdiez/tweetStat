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

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.tweetstat.injector.module.ApplicationModule;
import com.mgdiez.tweetstat.view.activity.TweetStattBaseActivity;

import javax.inject.Singleton;

import dagger.Component;
import executor.RxBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    // where should I inject the dependencies?
    void inject(TweetStattBaseActivity tweetStattBaseActivity);

    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();

    RxBus getRxBus();


}
