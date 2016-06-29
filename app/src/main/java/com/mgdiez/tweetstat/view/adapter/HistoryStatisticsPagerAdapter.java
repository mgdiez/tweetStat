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
package com.mgdiez.tweetstat.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.view.fragment.statistics.HashtagsStatisticsFragment;
import com.mgdiez.tweetstat.view.fragment.statistics.HomeTimelineStatisticsFragment;
import com.mgdiez.tweetstat.view.fragment.statistics.SearchStatisticsFragment;
import com.mgdiez.tweetstat.view.fragment.statistics.UserTimelineStatisticsFragment;

public class HistoryStatisticsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public HistoryStatisticsPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return 4;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeTimelineStatisticsFragment.newInstance();
            case 1:
                return UserTimelineStatisticsFragment.newInstance();
            case 2:
                return SearchStatisticsFragment.newInstance();
            case 3:
                return HashtagsStatisticsFragment.newInstance();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.title_mytweets);
            case 1:
                return context.getString(R.string.title_timeline);
            case 2:
                return context.getString(R.string.title_search);
            case 3:
                return context.getString(R.string.title_hashtags);

        }
        return "";
    }
}