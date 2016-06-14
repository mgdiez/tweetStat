package com.mgdiez.tweetstat.view.activity;

import com.mgdiez.tweetstat.R;
import com.stephentuso.welcome.WelcomeScreenBuilder;
import com.stephentuso.welcome.util.WelcomeScreenConfiguration;

public class WelcomeActivity extends com.stephentuso.welcome.ui.WelcomeActivity {

    @Override
    protected WelcomeScreenConfiguration configuration() {
        return new WelcomeScreenBuilder(this)
                .theme(R.style.WelcomeScreenTheme_Light_SolidNavigation_UnderStatusBar)
                .defaultBackgroundColor(R.color.primary)
                .titlePage(R.drawable.logo, "Welcome to tweetStat", R.color.primary_light)
                .basicPage(R.drawable.statistics_icon, "Retrieve the data", "From your timeline, " +
                        "hashtags, search...", R.color.primary_dark)
                .basicPage(R.drawable.logo, "Generate statistics", "Save it", R.color.primary)
                .swipeToDismiss(true)
                .build();
    }


}
