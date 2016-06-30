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

import android.content.Context;

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.repository.StatisticsRepository;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.domain.repository.UserRepository;
import com.mgdiez.tweetstat.injector.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import executor.RxBus;

/**
 *
 * Component of Dagger2 for Application dependencies
 *
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();

    RxBus getRxBus();

    TweetsRepository tweetsRepository();

    StatisticsRepository statisticsRepository();

    UserRepository userRepository();

}
