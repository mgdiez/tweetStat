package com.mgdiez.tweetstat.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.mgdiez.domain.bean.StatisticBo;
import com.mgdiez.domain.bean.TweetBo;
import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.interactor.GetHomeTimelineUseCase;
import com.mgdiez.domain.interactor.GetSearchUseCase;
import com.mgdiez.domain.interactor.GetTimelineUseCase;
import com.mgdiez.domain.interactor.GetTweetsByHashtagUseCase;
import com.mgdiez.domain.interactor.PersistStatisticUseCase;
import com.mgdiez.domain.repository.StatisticsRepository;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.UIThread;
import com.mgdiez.tweetstat.view.activity.FullGraphActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import executor.JobExecutor;
import repository.StatisticsRepositoryImpl;
import repository.TweetsRepositoryImpl;
import rx.Subscriber;

public class FullGraphPresenter implements Presenter {

    private static final String TAG = FullGraphPresenter.class.getName();
    private FullGraphActivity view;
    private final String type;
    private final String extra_type;

    private HashMap<String, Integer> data;
    int nTweets = 0;
    private List<TweetBo> boList;

    String selectedOption = "DAY";

    public FullGraphPresenter(FullGraphActivity fullGraphActivity, String TYPE, String EXTRA_TYPE, String selectedOption) {
        view = fullGraphActivity;
        type = TYPE;
        extra_type = EXTRA_TYPE;
        retrieveData();
        this.selectedOption = selectedOption;
        //ge = new GetHashtagsUseCase(jobExecutor, postExecutionThread, tweetsRepository);
    }

    private void retrieveData() {
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        TweetsRepository tweetsRepository = new TweetsRepositoryImpl(view);
        String userName = view.getSharedPreferences(view.getString(R.string.preference_file_key), Context.MODE_PRIVATE).getString(view.getString(R.string.username), "");

        switch (type){
            case "MY_TWEETS":
                new GetTimelineUseCase(jobExecutor, postExecutionThread, tweetsRepository).execute(false, userName, new GetTweetsStatisticsSubscriber());
                break;

            case "TIMELINE":
                new GetHomeTimelineUseCase(jobExecutor, postExecutionThread, tweetsRepository).execute(userName, false, new GetTweetsStatisticsSubscriber());
                break;

            case "SEARCH":
                new GetSearchUseCase(jobExecutor, postExecutionThread, tweetsRepository).execute(extra_type, false, new GetTweetsStatisticsSubscriber());
                break;

            case "HASHTAGS":
                new GetTweetsByHashtagUseCase(jobExecutor, postExecutionThread, tweetsRepository).execute(extra_type, true, new GetTweetsStatisticsSubscriber());
                break;

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
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        StatisticsRepository statisticsRepository = new StatisticsRepositoryImpl(view);

        PersistStatisticUseCase persistStatisticUseCase = new PersistStatisticUseCase(jobExecutor, postExecutionThread, statisticsRepository);
        StatisticBo bo = generateBoFromData();

        persistStatisticUseCase.execute(bo, new Subscriber() {
            @Override
            public void onCompleted() {
                //TODO
            }

            @Override
            public void onError(Throwable e) {
                //TODO
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
        bo.setDateGenerated(new Date().toString());

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
            onCompleted();
        }
    }
}
