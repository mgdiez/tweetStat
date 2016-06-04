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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.TweetStatConstants;
import com.mgdiez.tweetstat.view.CircleTransformation;
import com.mgdiez.tweetstat.view.adapter.TweetStatPagerAdapter;
import com.mgdiez.tweetstat.view.fragment.HashtagsFragment;
import com.mgdiez.tweetstat.view.fragment.SearchFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import butterknife.ButterKnife;
import executor.RxBus;
import executor.events.StatisticsRequestEvent;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private TextView username;
    private ImageView userProfilePicture;
    private TextView userPublicName;
    private TextView txtTweets;
    private TextView txtFollowing;
    private TextView txtFollowers;
    private LinearLayout relativeLayout;

    public String usernameTxt;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

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
        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ButterKnife.bind(this);
        initializeDrawerLayout();
        initializeUserProfile();
        //init rxBus
        rxBus = RxBus.getInstance();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(this, HistoryStatisticsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

    private void initializeUserProfile() {
        TwitterSession session =
                Twitter.getSessionManager().getActiveSession();
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {

                        User user = userResult.data;
                        usernameTxt = user.screenName;
                        String twitterImage = user.profileImageUrl.replace(TweetStatConstants.NORMAL_SIZE, "");
                        String userpublicName = user.name;
                        String backgroundImage = user.profileBannerUrl;
                        int followers = user.followersCount;
                        int following = user.friendsCount;
                        int ntweets = user.statusesCount;

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
                        Picasso.with(getApplicationContext()).load(backgroundImage + TweetStatConstants.MOBILE_SIZE).resizeDimen(R.dimen.nav_header_width_picasso, R.dimen.nav_header_height).centerCrop().into(new Target() {
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
                    public void failure(TwitterException e) {
                    }
                });
    }

    private void initializeDrawerLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxBus.send(new StatisticsRequestEvent());
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
                        String query = ((SearchFragment) searchFragment).getQuery();
                        if (!query.isEmpty()) {
                            intent.putExtra(TweetStatConstants.SEARCH, query);
                            startActivity(intent);
                        }
                        break;

                    case 3:
                        Fragment hashtagFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
                        intent.putExtra(TweetStatConstants.HASHTAGS, ((HashtagsFragment) hashtagFragment).getHashtagQuery());
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

    public String getUsernameTxt(){
        return usernameTxt;
    }

}
