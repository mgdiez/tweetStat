package com.mgdiez.tweetstat.injector.component;

import com.mgdiez.tweetstat.injector.PerActivity;
import com.mgdiez.tweetstat.injector.module.ActivityModule;
import com.mgdiez.tweetstat.injector.module.TweetsModule;
import com.mgdiez.tweetstat.view.activity.MainActivity;
import com.mgdiez.tweetstat.view.fragment.tweets.HashtagsTweetsFragment;
import com.mgdiez.tweetstat.view.fragment.tweets.HomeTimelineTweetsFragment;
import com.mgdiez.tweetstat.view.fragment.tweets.SearchTweetsFragment;
import com.mgdiez.tweetstat.view.fragment.tweets.UserTimelineTweetsFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, TweetsModule.class})
public interface TweetsComponent extends ActivityComponent {

    void inject(MainActivity homeActivity);

    void inject(HomeTimelineTweetsFragment homeTimelineTweetsFragment);

    void inject(SearchTweetsFragment searchTweetsFragment);

    void inject(UserTimelineTweetsFragment userTimelineTweetsFragment);

    void inject(HashtagsTweetsFragment hashtagsTweetsFragment);

}