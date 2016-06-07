package com.mgdiez.tweetstat.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mgdiez.domain.bean.TweetBo;
import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.interactor.GetHashtagsUseCase;
import com.mgdiez.domain.interactor.GetHomeTimelineUseCase;
import com.mgdiez.domain.interactor.GetSearchUseCase;
import com.mgdiez.domain.interactor.GetTimelineUseCase;
import com.mgdiez.domain.interactor.GetTweetsByHashtagUseCase;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.UIThread;
import com.mgdiez.tweetstat.view.activity.FullGraphActivity;
import com.mgdiez.tweetstat.view.fragment.HashtagsFragment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import executor.JobExecutor;
import repository.TweetsRepositoryImpl;
import rx.Subscriber;

public class FullGraphPresenter implements Presenter {

    private static final String TAG = FullGraphPresenter.class.getName();
    private FullGraphActivity view;
    private final String type;
    private final String extra_type;

    private HashMap<String, Integer> data;
    private List<TweetBo> boList;

    String selectedOption = "DAY";

    public FullGraphPresenter(FullGraphActivity fullGraphActivity, String TYPE, String EXTRA_TYPE) {
        view = fullGraphActivity;
        type = TYPE;
        extra_type = EXTRA_TYPE;
        retrieveData();
        selectedOption = getSelectedOption();
        //ge = new GetHashtagsUseCase(jobExecutor, postExecutionThread, tweetsRepository);
    }

    private String getSelectedOption() {
        return "DAY";
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
        int nTweets = 0;

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
