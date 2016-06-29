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
package com.mgdiez.tweetstat.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.mgdiez.domain.bean.StatisticBo;
import com.mgdiez.domain.bean.TweetBo;
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.interactor.statistics.GetStatisticByIdUseCase;
import com.mgdiez.domain.interactor.statistics.PersistStatisticUseCase;
import com.mgdiez.domain.interactor.tweets.GetHomeTimelineUseCase;
import com.mgdiez.domain.interactor.tweets.GetSearchUseCase;
import com.mgdiez.domain.interactor.tweets.GetTimelineUseCase;
import com.mgdiez.domain.interactor.tweets.GetTweetsByHashtagUseCase;
import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.injector.PerActivity;
import com.mgdiez.tweetstat.view.activity.FullGraphActivity;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Subscriber;

@PerActivity
public class FullGraphStatisticsPresenter implements StatisticsPresenter {

    private static final String TAG = FullGraphStatisticsPresenter.class.getName();

    private FullGraphActivity view;

    private String type;

    private String extra_type;

    private HashMap<String, Integer> data;

    int nTweets = 0;

    private List<TweetBo> boList;

    private int id = -1;

    String selectedOption = "DAY";

    private GetHomeTimelineUseCase getHomeTimelineUseCase;

    private GetTimelineUseCase getTimelineUseCase;

    private GetSearchUseCase getSearchUseCase;

    private GetTweetsByHashtagUseCase getTweetsByHashtagUseCase;

    private GetStatisticByIdUseCase getStatisticByIdUseCase;

    private PersistStatisticUseCase persistStatisticUseCase;

    @Inject
    public FullGraphStatisticsPresenter(@Named("getHomeTimelineUseCase") UseCase
                                                    getHomeTimelineUseCase,
                                        @Named("getTimelineUseCase") UseCase getTimelineUseCase,
                                        @Named("getSearchUseCase") UseCase getSearchUseCase,
                                        @Named("getTweetsByHashtagUseCase") UseCase
                                                    getTweetsByHashtagUseCase,
                                        @Named("getStatisticsByIdUseCase") UseCase
                                                    getStatisticByIdUseCase,
                                        @Named("getPersistStatisticsUseCase") UseCase
                                                    persistStatisticUseCase) {

        this.getHomeTimelineUseCase = (GetHomeTimelineUseCase) getHomeTimelineUseCase;
        this.getTimelineUseCase = (GetTimelineUseCase) getTimelineUseCase;
        this.getSearchUseCase = (GetSearchUseCase) getSearchUseCase;
        this.getTweetsByHashtagUseCase = (GetTweetsByHashtagUseCase) getTweetsByHashtagUseCase;
        this.getStatisticByIdUseCase = (GetStatisticByIdUseCase) getStatisticByIdUseCase;
        this.persistStatisticUseCase = (PersistStatisticUseCase) persistStatisticUseCase;
    }

    public void setInformationFromActivity(String TYPE, String EXTRA_TYPE, String selectedOption) {
        type = TYPE;
        extra_type = EXTRA_TYPE;
        this.selectedOption = selectedOption;
        retrieveData();
    }

    public void setInformationFromActivity(String TYPE, String EXTRA_TYPE, int id) {
        type = TYPE;
        extra_type = EXTRA_TYPE;
        this.id = id;
        retrieveData();
    }

    private void retrieveData() {
        if (id != -1) {
            getStatisticByIdUseCase.execute(id, new GetStatisticByIdSubscriber());

        } else {
            String userName = view.getSharedPreferences(view.getString(R.string
                    .preference_file_key), Context.MODE_PRIVATE).getString(view.getString(R
                    .string.username), "");

            switch (type) {
                case "MY_TWEETS":
                    getTimelineUseCase.execute(false, userName, new GetTweetsStatisticsSubscriber
                            ());
                    break;

                case "TIMELINE":
                    getHomeTimelineUseCase.execute(userName, false, new
                            GetTweetsStatisticsSubscriber());
                    break;

                case "SEARCH":
                    getSearchUseCase.execute(extra_type, false, new GetTweetsStatisticsSubscriber
                            ());
                    break;

                case "HASHTAGS":
                    getTweetsByHashtagUseCase.execute(extra_type, true, new
                            GetTweetsStatisticsSubscriber());
                    break;

            }
        }
    }

    public void setView(@NonNull FullGraphActivity fullGraphActivity) {
        this.view = fullGraphActivity;
    }

    private void setStatisticsData(int nTweets) {
        view.setStatisticsData(data, nTweets);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getHomeTimelineUseCase.unsubscribe();
        getTimelineUseCase.unsubscribe();
        getSearchUseCase.unsubscribe();
        getTweetsByHashtagUseCase.unsubscribe();
        getStatisticByIdUseCase.unsubscribe();
        persistStatisticUseCase.unsubscribe();
    }


    private void generateData() {
        data = new HashMap<>();

        //about Country
        if ("COUNTRY".equals(selectedOption)) {
            for (TweetBo bo : boList) {
                if (bo.getCountry() != null && !bo.getCountry().isEmpty()) {
                    nTweets++;
                    String city = bo.getCountry();
                    if (data.containsKey(city)) {
                        data.put(city, data.get(city) + 1);
                    } else {
                        data.put(city, 1);
                    }
                }
            }
        }
        //about City
        else if ("CITY".equals(selectedOption)) {
            for (TweetBo bo : boList) {
                if (bo.getCity() != null && !bo.getCity().isEmpty()) {
                    nTweets++;
                    String city = bo.getCity();
                    if (data.containsKey(city)) {
                        data.put(city, data.get(city) + 1);
                    } else {
                        data.put(city, 1);
                    }
                }
            }
        }
        //about Day
        else if ("DAY".equals(selectedOption)) {
            for (TweetBo bo : boList) {
                if (bo.getCreatedAt() != null && !bo.getCreatedAt().isEmpty()) {
                    nTweets++;
                    String city = DateHelper.getDayToStatistics(bo.getCreatedAt());
                    if (data.containsKey(city)) {
                        data.put(city, data.get(city) + 1);
                    } else {
                        data.put(city, 1);
                    }
                }
            }
        }
        setStatisticsData(nTweets);
    }

    public void persistStatistic() {
        StatisticBo bo = generateBoFromData();
        persistStatisticUseCase.execute(bo, new Subscriber() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {
                Toast.makeText(view, "Saved Succesfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private StatisticBo generateBoFromData() {
        StatisticBo bo = new StatisticBo();
        bo.setStatisticsData(data);
        bo.setNTweets(nTweets);
        bo.setType(type);
        bo.setSubType(extra_type);
        Date date = new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date);
        bo.setDateGenerated(stringDate);
        bo.setSelectedOption(selectedOption);

        return bo;
    }

    private class GetTweetsStatisticsSubscriber extends Subscriber<List<TweetBo>> {
        @Override
        public void onCompleted() {
            generateData();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(List<TweetBo> tweetBos) {
            boList = tweetBos;
        }
    }

    private class GetStatisticByIdSubscriber extends Subscriber<StatisticBo> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(StatisticBo statisticBo) {
            data = statisticBo.getStatisticsData();
            setStatisticsData(statisticBo.getNTweets());
        }
    }
}
