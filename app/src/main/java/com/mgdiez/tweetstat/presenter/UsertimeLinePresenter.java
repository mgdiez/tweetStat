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
import com.mgdiez.domain.interactor.GetHomeTimelineUseCase;
import com.mgdiez.domain.interactor.GetTimelineUseCase;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.tweetstat.UIThread;
import com.mgdiez.tweetstat.model.TweetModel;
import com.mgdiez.tweetstat.model.mapper.TweetModelMapper;
import com.mgdiez.tweetstat.view.fragment.HomeTimelineFragment;
import com.mgdiez.tweetstat.view.fragment.UsertimeLineFragment;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.List;

import executor.JobExecutor;
import repository.TweetsRepositoryImpl;
import rx.Subscriber;

public class UsertimeLinePresenter implements Presenter {

    private static final String TAG = HomeTimelinePresenter.class.getName();

    private UsertimeLineFragment view;
    private GetTimelineUseCase getTimelineUseCase;
    private List<TweetModel> models;


    public UsertimeLinePresenter(UsertimeLineFragment usertimeLineFragment) {
        view = usertimeLineFragment;
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        TweetsRepository tweetsRepository = new TweetsRepositoryImpl(view.getContext());
        getTimelineUseCase = new GetTimelineUseCase(jobExecutor, postExecutionThread, tweetsRepository);
    }

    public void setView(@NonNull UsertimeLineFragment usertimeLineFragment) {
        this.view = usertimeLineFragment;
    }

    private void setModels() {
        view.setItems(models);
    }

    private void stopRefresh() {
        view.stopRefresh();
    }

    private void startRefresh() {
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
        getTimelineUseCase.unsubscribe();
    }

    public void getTimeline(String userName, boolean refresh) {
        startRefresh();
        getTimelineUseCase.execute(refresh, userName, new GetTimelineSubscriber());
    }

    private class GetTimelineSubscriber extends Subscriber<List<TweetBo>> {
        @Override
        public void onCompleted() {
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
            models = TweetModelMapper.toModel(tweetBos);
        }
    }
}
