package com.mgdiez.tweetstat.injector.component;

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.tweetstat.injector.module.ApplicationModule;
import com.mgdiez.tweetstat.view.activity.TweetStattBaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    // where should I inject the dependencies?
    void inject(TweetStattBaseActivity tweetStattBaseActivity);

    ThreadExecutor getThreadExecutor();

    PostExecutionThread getPostExecutionThread();


}
