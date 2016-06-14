package com.mgdiez.tweetstat.injector.module;

import android.content.Context;

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.tweetstat.TweetStattApplication;
import com.mgdiez.tweetstat.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import executor.JobExecutor;

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

}