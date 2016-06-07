package com.mgdiez.tweetstat.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.view.fragment.HomeTimelineStatisticsFragment;

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
            case 0: // Fragment # 0 - This will show FirstFragment
                return HomeTimelineStatisticsFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return HomeTimelineStatisticsFragment.newInstance();
                //return HomeTimelineTweetsFragment.newInstance();
            case 2: // Fragment # 1 - This will show SecondFragment
                return HomeTimelineStatisticsFragment.newInstance();
                //return SearchTweetsFragment.newInstance();
            case 3:
                return HomeTimelineStatisticsFragment.newInstance();
                //return HashtagsTweetsFragment.newInstance();

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
        return  "";
    }
}