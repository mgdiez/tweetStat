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
package com.mgdiez.tweetstat.injector.module;

import android.content.Context;

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.repository.StatisticsRepository;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.domain.repository.UserRepository;
import com.mgdiez.tweetstat.TweetStattApplication;
import com.mgdiez.tweetstat.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import executor.JobExecutor;
import executor.RxBus;
import repository.StatisticsRepositoryImpl;
import repository.TweetsRepositoryImpl;
import repository.UserRepositoryImpl;

@Module
public class ApplicationModule {

    private final TweetStattApplication application;

    public ApplicationModule(TweetStattApplication application) {
        this.application = application;
    }

    // provide dependencies
    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    RxBus provideRxBus(){
        return RxBus.getInstance();
    }

    @Provides
    @Singleton
    TweetsRepository provideTweetsRepository(TweetsRepositoryImpl tweetsRepository) {
        return tweetsRepository;
    }

    @Provides
    @Singleton
    StatisticsRepository provideStatisticsRepository(StatisticsRepositoryImpl statisticsRepository){
        return statisticsRepository;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserRepositoryImpl userRepository){
        return userRepository;
    }
}