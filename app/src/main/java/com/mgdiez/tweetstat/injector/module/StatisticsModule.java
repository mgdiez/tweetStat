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
import com.mgdiez.domain.interactor.statistics.GetHashtagsStatisticsUseCase;
import com.mgdiez.domain.interactor.statistics.GetHomeTimelineStatisticsUseCase;
import com.mgdiez.domain.interactor.statistics.GetSearchStatisticsUseCase;
import com.mgdiez.domain.interactor.statistics.GetTimelineStatisticsUseCase;
import com.mgdiez.domain.repository.StatisticsRepository;
import com.mgdiez.tweetstat.injector.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class StatisticsModule {

    public StatisticsModule() {
    }

    @Provides
    @PerActivity
    @Named("getHashtagsStatisticUseCase")
    UseCase provideGetHashtagsStatisticsUseCase(StatisticsRepository statisticsRepository, ThreadExecutor threadExecutor,

                                                PostExecutionThread postExecutionThread) {
        return new GetHashtagsStatisticsUseCase(threadExecutor, postExecutionThread, statisticsRepository);
    }

    @Provides
    @PerActivity
    @Named("getHomeTimelineStatisticsUseCase")
    UseCase provideGetHomeTimelineStatisticsUseCase(StatisticsRepository statisticsRepository,
                                                    ThreadExecutor threadExecutor,
                                                    PostExecutionThread postExecutionThread) {
        return new GetHomeTimelineStatisticsUseCase(threadExecutor, postExecutionThread, statisticsRepository);
    }


    @Provides
    @PerActivity
    @Named("getSearchStatisticsUseCase")
    UseCase provideGetSearchStatisticsUseCase(StatisticsRepository statisticsRepository, ThreadExecutor threadExecutor,
                                              PostExecutionThread postExecutionThread) {
        return new GetSearchStatisticsUseCase(threadExecutor, postExecutionThread, statisticsRepository);
    }


    @Provides
         @PerActivity
         @Named("getTimelineUseStatisticsUseCase")
         UseCase provideGetTimelineStatisticsUseCase(StatisticsRepository statisticsRepository, ThreadExecutor threadExecutor,
                                                     PostExecutionThread postExecutionThread) {
        return new GetTimelineStatisticsUseCase(threadExecutor, postExecutionThread, statisticsRepository);
    }
}