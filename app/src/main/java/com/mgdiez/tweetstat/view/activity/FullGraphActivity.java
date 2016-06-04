package com.mgdiez.tweetstat.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.TweetStatConstants;
import com.mgdiez.tweetstat.view.adapter.HistoryStatisticsAdapter;


public class FullGraphActivity extends BaseActivity {

    private String TYPE = "";
    private String EXTRA_TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_statistics_activity);
        getExtras();
        TabLayout tabLayout;
        ViewPager viewPager;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && toolbar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(getString(R.string.full_graph_activity_title));
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new HistoryStatisticsAdapter(getApplicationContext(), getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            if (extras.containsKey(TweetStatConstants.MY_TWEETS)) {
                TYPE = TweetStatConstants.MY_TWEETS;
            }
            else if (extras.containsKey(TweetStatConstants.TIMELINE)) {
                TYPE = TweetStatConstants.TIMELINE;
            }
            else if (extras.containsKey(TweetStatConstants.SEARCH)) {
                TYPE = TweetStatConstants.SEARCH;
                EXTRA_TYPE = extras.getString(TweetStatConstants.SEARCH);
            }
            else {
                TYPE = TweetStatConstants.HASHTAGS;
                EXTRA_TYPE = extras.getString(TweetStatConstants.HASHTAGS);
            }
        }

        Toast.makeText(FullGraphActivity.this, TYPE + " " + EXTRA_TYPE, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
