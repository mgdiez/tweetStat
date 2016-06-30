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

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.interactor.statistics.GetStatisticByIdUseCase;
import com.mgdiez.domain.interactor.statistics.PersistStatisticUseCase;
import com.mgdiez.domain.interactor.tweets.GetHashtagsUseCase;
import com.mgdiez.domain.interactor.tweets.GetHomeTimelineUseCase;
import com.mgdiez.domain.interactor.tweets.GetSearchUseCase;
import com.mgdiez.domain.interactor.tweets.GetTimelineUseCase;
import com.mgdiez.domain.interactor.tweets.GetTweetsByHashtagUseCase;
import com.mgdiez.domain.interactor.user.GetUserDataUseCase;
import com.mgdiez.domain.repository.StatisticsRepository;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.domain.repository.UserRepository;
import com.mgdiez.tweetstat.injector.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TweetsModule {

    public TweetsModule() {
    }

    // provide dependencies
    @Provides
    @PerActivity
    @Named("getHashtagsUseCase")
    UseCase provideGetHashtagsUseCase(TweetsRepository tweetsRepository, ThreadExecutor
            threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        return new GetHashtagsUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }

    @Provides
    @PerActivity
    @Named("getHomeTimelineUseCase")
    UseCase provideGetHomeTimelineUseCase(TweetsRepository tweetsRepository, ThreadExecutor
            threadExecutor,
                                          PostExecutionThread postExecutionThread) {
        return new GetHomeTimelineUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }


    @Provides
    @PerActivity
    @Named("getSearchUseCase")
    UseCase provideGetSearchUseCase(TweetsRepository tweetsRepository, ThreadExecutor
            threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        return new GetSearchUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }


    @Provides
    @PerActivity
    @Named("getTimelineUseCase")
    UseCase provideGetTimelineUseCase(TweetsRepository tweetsRepository, ThreadExecutor
            threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        return new GetTimelineUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }

    @Provides
    @PerActivity
    @Named("getTweetsByHashtagUseCase")
    UseCase provideGetTweetsByHashtagUseCase(TweetsRepository tweetsRepository, ThreadExecutor
            threadExecutor,
                                             PostExecutionThread postExecutionThread) {
        return new GetTweetsByHashtagUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }

    @Provides
    @PerActivity
    @Named("getStatisticsByIdUseCase")
    UseCase provideGetStatisticsByIdUseCase(StatisticsRepository statisticsRepository,
                                            ThreadExecutor threadExecutor,
                                            PostExecutionThread postExecutionThread) {
        return new GetStatisticByIdUseCase(threadExecutor, postExecutionThread,
                statisticsRepository);
    }

    @Provides
    @PerActivity
    @Named("getPersistStatisticsUseCase")
    UseCase providePersistStatisticUseCase(StatisticsRepository statisticsRepository,
                                           ThreadExecutor threadExecutor,
                                           PostExecutionThread postExecutionThread) {
        return new PersistStatisticUseCase(threadExecutor, postExecutionThread,
                statisticsRepository);
    }

    @Provides
    @PerActivity
    @Named("getUserDataUseCase")
    UseCase provideGetUserDataUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        return new GetUserDataUseCase(threadExecutor, postExecutionThread, userRepository);
    }
}