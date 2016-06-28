/**
 * Copyright (C) 2016 Marc Gonzalez Diez Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mgdiez.tweetstat.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.model.UserModel;
import com.mgdiez.tweetstat.presenter.MainUserPresenter;
import com.mgdiez.tweetstat.view.adapter.TweetStatPagerAdapter;
import com.mgdiez.tweetstat.view.fragment.tweets.HashtagsTweetsFragment;
import com.mgdiez.tweetstat.view.fragment.tweets.SearchTweetsFragment;
import com.mgdiez.tweetstat.view.fragment.util.CircleTransformation;
import com.mgdiez.tweetstat.view.fragment.util.TweetStatConstants;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twitter.sdk.android.core.TwitterCore;

import butterknife.ButterKnife;
import executor.RxBus;
import executor.events.ConnectionEvent;
import executor.events.NoConnectionEvent;
import repository.NetworkUtil;

public class MainActivity extends TweetStattBaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView username;
    private ImageView userProfilePicture;
    private TextView userPublicName;
    private TextView txtTweets;
    private TextView txtFollowing;
    private TextView txtFollowers;

    private LinearLayout relativeLayout;
    public String usernameTxt;
    private ViewPager viewPager;

    private RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        this.registerReceiver(this.mConnReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        setContentView(R.layout.activity_main);
        findViews();
        ButterKnife.bind(this);
        initializeDrawerLayout();
        rxBus = RxBus.getInstance();
        MainUserPresenter mainPresenter;
        mainPresenter = new MainUserPresenter(this);
        mainPresenter.getUserData(NetworkUtil.isNetworkAvailable(this));
        checkIfSnackbarNeeded();
    }

    private void checkIfSnackbarNeeded() {
        if(!NetworkUtil.isNetworkAvailable(this)){
            rxBus.send(new NoConnectionEvent());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(this.mConnReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            this.unregisterReceiver(this.mConnReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.toString());
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.statsActivity) {
            // Handle the camera action
            Intent intent = new Intent(this, HistoryStatisticsActivity.class);
            startActivity(intent);
        } else if (id == R.id.share) {
            Toast.makeText(MainActivity.this, "TODO: Share", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.logout) {
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initializeDrawerLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentItem =  viewPager.getCurrentItem();
                Intent intent = new Intent(MainActivity.this, FullGraphActivity.class);
                switch (currentItem) {

                    case 0:
                        intent.putExtra(TweetStatConstants.MY_TWEETS, "");
                        startActivity(intent);

                        break;

                    case 1:
                        intent.putExtra(TweetStatConstants.TIMELINE, "");
                        startActivity(intent);

                        break;

                    case 2:
                        Fragment searchFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
                        String query = ((SearchTweetsFragment) searchFragment).getQuery();
                        if (!query.isEmpty()) {
                            intent.putExtra(TweetStatConstants.SEARCH, query);
                            startActivity(intent);
                        }
                        break;

                    case 3:
                        Fragment hashtagFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
                        intent.putExtra(TweetStatConstants.HASHTAGS, ((HashtagsTweetsFragment) hashtagFragment).getHashtagQuery());
                        startActivity(intent);

                        break;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
            boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

            NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

            if(currentNetworkInfo.isConnected()){
                rxBus.send(new ConnectionEvent());
            }else{
                rxBus.send(new NoConnectionEvent());
            }
        }
    };

    private void findViews() {
        Toolbar toolbar;
        TabLayout tabLayout;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView =  navigationView.getHeaderView(0);
        relativeLayout = (LinearLayout) headerView.findViewById(R.id.parentLayout_header);
        userProfilePicture = (ImageView) headerView.findViewById(R.id.imageView_userProfile);
        username = (TextView) headerView.findViewById(R.id.textView_userName);
        userPublicName = (TextView) headerView.findViewById(R.id.textView_userPublicName);

        txtTweets = (TextView) headerView.findViewById(R.id.txtTweets);
        txtFollowers = (TextView) headerView.findViewById(R.id.txtFollowers);
        txtFollowing = (TextView) headerView.findViewById(R.id.txtFollowing);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetStatPagerAdapter(getApplicationContext(), getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(1);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setUser(UserModel userModel) {
        usernameTxt = userModel.getUserName();

        SharedPreferences.Editor editor = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
        editor.putString(getString(R.string.username), usernameTxt);
        editor.apply();


        String twitterImage = userModel.getTwitterImage();
        String userpublicName = userModel.getUserPublicName();
        final String backgroundImage = userModel.getBackgroundImage();
        int followers = userModel.getFollowers();
        int following = userModel.getFollowing();
        int ntweets = userModel.getNTweets();

        // Profile Image
        Picasso.with(getApplicationContext())
                .load(twitterImage)
                .error(R.drawable.logo)
                .placeholder(R.drawable.logo)
                .transform(new CircleTransformation())
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerCrop()
                .into(userProfilePicture);
        username.setText("@" + usernameTxt);
        userPublicName.setText(userpublicName);
        txtFollowing.setText(String.valueOf(following));
        txtFollowers.setText(String.valueOf(followers));
        txtTweets.setText(String.valueOf(ntweets));

        // Background Image
        Picasso.with(getApplicationContext()).load(backgroundImage)
                .resizeDimen(R.dimen.nav_header_width_picasso, R.dimen.nav_header_height)
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        relativeLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Picasso.with(getApplicationContext()).load(backgroundImage)
                                .resizeDimen(R.dimen.nav_header_width_picasso, R.dimen.nav_header_height)
                                .centerCrop()
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        relativeLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
}
