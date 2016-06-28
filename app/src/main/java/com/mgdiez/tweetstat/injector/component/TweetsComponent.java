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