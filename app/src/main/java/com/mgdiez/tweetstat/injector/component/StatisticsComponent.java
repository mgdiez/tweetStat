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
import com.mgdiez.tweetstat.injector.module.StatisticsModule;
import com.mgdiez.tweetstat.view.activity.HistoryStatisticsActivity;
import com.mgdiez.tweetstat.view.fragment.statistics.HashtagsStatisticsFragment;
import com.mgdiez.tweetstat.view.fragment.statistics.HomeTimelineStatisticsFragment;
import com.mgdiez.tweetstat.view.fragment.statistics.SearchStatisticsFragment;
import com.mgdiez.tweetstat.view.fragment.statistics.UserTimelineStatisticsFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, StatisticsModule.class})
public interface StatisticsComponent extends ActivityComponent {

    void inject(HistoryStatisticsActivity historyStatisticsActivity);

    void inject(HomeTimelineStatisticsFragment homeTimelineStatisticsFragment);

    void inject(SearchStatisticsFragment searchStatisticsFragment);

    void inject(UserTimelineStatisticsFragment userTimelineStatisticsFragment);

    void inject(HashtagsStatisticsFragment hashtagsStatisticsFragment);

}