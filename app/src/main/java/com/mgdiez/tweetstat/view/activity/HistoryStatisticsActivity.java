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
package com.mgdiez.tweetstat.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.injector.HasComponent;
import com.mgdiez.tweetstat.injector.component.DaggerStatisticsComponent;
import com.mgdiez.tweetstat.injector.component.StatisticsComponent;
import com.mgdiez.tweetstat.injector.module.StatisticsModule;
import com.mgdiez.tweetstat.view.adapter.HistoryStatisticsPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activity used to contain all Fragments related with Statistics generated
 */
public class HistoryStatisticsActivity extends TweetStattBaseActivity implements
        HasComponent<StatisticsComponent> {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    private StatisticsComponent statisticsComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_statistics_activity);
        ButterKnife.bind(this);
        initToolbar();
        initViewPagerAndToolbar();
        initializeInjector();
    }

    private void initializeInjector() {
        this.statisticsComponent = DaggerStatisticsComponent.builder()
                .applicationComponent((this).getApplicationComponent())
                .activityModule((this).getActivityModule())
                .statisticsModule(new StatisticsModule())
                .build();
        this.getComponent().inject(this);
    }

    private void initViewPagerAndToolbar() {
        viewPager.setAdapter(new HistoryStatisticsPagerAdapter(getApplicationContext(),
                getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && toolbar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(getString(R.string.statistics_history_activity_title));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public StatisticsComponent getComponent() {
        return statisticsComponent;
    }
}
