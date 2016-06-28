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

import android.support.annotation.NonNull;
import android.util.Log;

import com.mgdiez.domain.bean.TweetBo;
import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.interactor.tweets.GetHomeTimelineUseCase;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.tweetstat.UIThread;
import com.mgdiez.tweetstat.model.TweetModel;
import com.mgdiez.tweetstat.model.mapper.TweetModelMapper;
import com.mgdiez.tweetstat.view.fragment.tweets.HomeTimelineTweetsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import executor.JobExecutor;
import repository.TweetsRepositoryImpl;
import rx.Subscriber;

public class HomeTimelineTweetsPresenter implements TweetsPresenter {

    private static final String TAG = HomeTimelineTweetsPresenter.class.getName();

    private HomeTimelineTweetsFragment view;
    private GetHomeTimelineUseCase getHomeTimelineUseCase;
    private List<TweetModel> models = new ArrayList<>();


    public HomeTimelineTweetsPresenter(HomeTimelineTweetsFragment homeTimelineTweetsFragment) {
        view = homeTimelineTweetsFragment;
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        TweetsRepository tweetsRepository = new TweetsRepositoryImpl(view.getContext());
        getHomeTimelineUseCase = new GetHomeTimelineUseCase(jobExecutor, postExecutionThread, tweetsRepository);
    }

    public void setView(@NonNull HomeTimelineTweetsFragment homeTimelineTweetsFragment) {
        this.view = homeTimelineTweetsFragment;
    }

    private void setModels() {
        view.setItems(models);
    }

    private void stopRefresh() {
        view.stopRefresh();
    }

    private void startRefresh() {
        models.clear();
        view.startRefresh();
    }

    private void showMessage(String message) {
        view.showMessage(message);
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    @Override
    public void resume() {

    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    @Override
    public void pause() {

    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    @Override
    public void destroy() {
        getHomeTimelineUseCase.unsubscribe();
    }


    public void getHometimeline(String userName, boolean refresh) {
        startRefresh();
        getHomeTimelineUseCase.execute(userName, refresh, new GetTimelineSubscriber());
    }

    private class GetTimelineSubscriber extends Subscriber<List<TweetBo>> {
        @Override
        public void onCompleted() {
            setModels();
            setModels();
            stopRefresh();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            showMessage(e.getMessage());
            stopRefresh();
        }

        @Override
        public void onNext(List<TweetBo> tweetBos) {
            if (models.isEmpty()) {
                models = TweetModelMapper.toModel(tweetBos);
            }

            Collections.sort(models, new Comparator<TweetModel>() {
                @Override
                public int compare(TweetModel lhs, TweetModel rhs) {
                    if (lhs.getId() < rhs.getId()) return 1;
                    else if (lhs.getId() == rhs.getId()) return 0;
                    else return -1;
                }
            });
            onCompleted();
        }
    }
}
