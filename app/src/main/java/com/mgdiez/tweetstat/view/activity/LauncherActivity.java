package com.mgdiez.tweetstat.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.mgdiez.tweetstat.R;
import com.stephentuso.welcome.WelcomeScreenHelper;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import net.ApiConstants;

import io.fabric.sdk.android.Fabric;

public class LauncherActivity extends BaseActivity{

    private WelcomeScreenHelper welcomeScreen;

    private SharedPreferences sharedPref;

    private boolean showWelcomeScreen = true;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initShared();

        if (showWelcomeScreen) {
            welcomeScreen = new WelcomeScreenHelper(this, WelcomeActivity.class);
            welcomeScreen.show(savedInstanceState);
        }

        else if (TwitterCore.getInstance().getSessionManager().getActiveSession() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initShared() {
          sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        showWelcomeScreen = sharedPref.getBoolean(getString(R.string.welcome_screen_flag), true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (welcomeScreen != null) {
            welcomeScreen.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == WelcomeScreenHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
            String welcomeKey = data.getStringExtra(WelcomeActivity.WELCOME_SCREEN_KEY);
            if (resultCode == RESULT_OK) {
                // Code here will run if the welcome screen was completed
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            saveWelcomeDone();
        }

    }
    private void saveWelcomeDone() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.welcome_screen_flag), false);
        editor.apply();
    }
}
