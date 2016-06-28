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

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.presenter.FullGraphStatisticsPresenter;
import com.mgdiez.tweetstat.view.fragment.util.TweetStatConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FullGraphActivity extends TweetStattBaseActivity {

    @Bind(R.id.chart)
    PieChart pieChart;

    @Bind(R.id.btnStartStatistic)
    ImageButton btnStartStatistic;

    @Bind(R.id.optionLayout)
    LinearLayout optionLayout;

    @Bind(R.id.chartLayout)
    LinearLayout chartLayout;

    @Bind(R.id.optionSelected)
    RadioGroup optionSelected;

    @Bind(R.id.btnSave)
    ImageButton btnSave;

    private String TYPE = "";
    private String EXTRA_TYPE = "";
    private HashMap<String, Integer> statisticsData;
    private FullGraphStatisticsPresenter fullGraphPresenter;
    private boolean fromStatisticsList = false;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_graph_activity);
        getExtras();
        initToolbar();
        ButterKnife.bind(this);
        initChart();
        if (fromStatisticsList) {
            startPresenter(id);
        } else {
            btnStartStatistic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int optionId = optionSelected.getCheckedRadioButtonId();
                    if (optionId != -1) {
                        String selectedOption = "";
                        switch (optionId) {
                            case R.id.countryOption:
                                selectedOption = "COUNTRY";
                                break;
                            case R.id.dayOption:
                                selectedOption = "DAY";
                                break;
                            case R.id.cityOption:
                                selectedOption = "CITY";
                                break;
                        }
                        startPresenter(selectedOption);
                    }
                }
            });
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullGraphPresenter.persistStatistic();
                finish();
            }
        });
    }

    private void startPresenter(String selectedOption) {
        fullGraphPresenter = new FullGraphStatisticsPresenter(this, TYPE, EXTRA_TYPE, selectedOption);
    }

    private void startPresenter(int id) {
        fullGraphPresenter = new FullGraphStatisticsPresenter(this, TYPE, EXTRA_TYPE, id);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } if (toolbar != null) {
            toolbar.setTitle(getString(R.string.full_graph_activity_title));
        }
    }

    private void initChart() {
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
    }

    private void setData() {

        ArrayList<Entry> yVals1 = new ArrayList<>();

        ArrayList<String> xVals = new ArrayList<>();
        Iterator it = statisticsData.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            yVals1.add(new Entry((Integer) e.getValue(), i));
            xVals.add((String) e.getKey());
            i++;
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Statistics Chart\ngenerated by TweetStat");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 16, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 16, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 13, s.length(), 0);
        return s;
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

            if (extras.containsKey("id")){
                id = extras.getInt("id");
                fromStatisticsList = true;
            }
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

    public void setStatisticsData(HashMap<String, Integer> statisticsData, int nTweets) {
        this.statisticsData = statisticsData;
        pieChart.setDescription(nTweets + " tweets used with the information required");
        setData();
        optionLayout.setVisibility(View.INVISIBLE);
        chartLayout.setVisibility(View.VISIBLE);
        if (id != -1) {
            btnSave.setVisibility(View.INVISIBLE);
        }
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }
}
