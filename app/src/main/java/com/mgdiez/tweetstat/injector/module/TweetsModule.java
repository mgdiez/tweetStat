package com.mgdiez.tweetstat.injector.module;

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.interactor.tweets.GetHashtagsUseCase;
import com.mgdiez.domain.interactor.tweets.GetHomeTimelineUseCase;
import com.mgdiez.domain.interactor.tweets.GetSearchUseCase;
import com.mgdiez.domain.interactor.tweets.GetTimelineUseCase;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.tweetstat.injector.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TweetsModule {

    public TweetsModule() {
    }

    @Provides
    @PerActivity
    @Named("getHashtagsUseCase")
    UseCase provideGetHashtagsUseCase(TweetsRepository tweetsRepository, ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        return new GetHashtagsUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }

    @Provides
    @PerActivity
    @Named("getHomeTimelineUseCase")
    UseCase provideGetHomeTimelineUseCase(TweetsRepository tweetsRepository, ThreadExecutor threadExecutor,
                                          PostExecutionThread postExecutionThread) {
        return new GetHomeTimelineUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }


    @Provides
    @PerActivity
    @Named("getSearchUseCase")
    UseCase provideGetSearchUseCase(TweetsRepository tweetsRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        return new GetSearchUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }


    @Provides
    @PerActivity
    @Named("getTimelineUseCase")
    UseCase provideGetTimelineUseCase(TweetsRepository tweetsRepository, ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        return new GetTimelineUseCase(threadExecutor, postExecutionThread, tweetsRepository);
    }
}