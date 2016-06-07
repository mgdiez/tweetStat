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

import com.mgdiez.domain.bean.HashtagBo;
import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.interactor.GetHashtagsUseCase;
import com.mgdiez.domain.repository.TweetsRepository;
import com.mgdiez.tweetstat.UIThread;
import com.mgdiez.tweetstat.model.HashtagModel;
import com.mgdiez.tweetstat.model.mapper.HashtagModelMapper;
import com.mgdiez.tweetstat.view.fragment.HashtagsTweetsFragment;

import java.util.List;

import executor.JobExecutor;
import repository.TweetsRepositoryImpl;
import rx.Subscriber;

public class HashtagsPresenter implements Presenter {

    private static final String TAG = HomeTimelinePresenter.class.getName();

    private HashtagsTweetsFragment view;
    private GetHashtagsUseCase getHashtagsUseCase;
    private List<HashtagModel> models;


    public HashtagsPresenter(HashtagsTweetsFragment hashtagsTweetsFragment) {
        view = hashtagsTweetsFragment;
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        TweetsRepository tweetsRepository = new TweetsRepositoryImpl(view.getContext());
        getHashtagsUseCase = new GetHashtagsUseCase(jobExecutor, postExecutionThread, tweetsRepository);
    }

    public void setView(@NonNull HashtagsTweetsFragment hashtagsTweetsFragment) {
        this.view = hashtagsTweetsFragment;
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


    public void getHashtags(){
        getHashtagsUseCase.execute(true, new GetHashtagsSubscriber());
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
        getHashtagsUseCase.unsubscribe();
    }


    private class GetHashtagsSubscriber extends Subscriber<List<HashtagBo>> {
        @Override
        public void onCompleted() {
            setModels();
            stopRefresh();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            stopRefresh();
        }

        @Override
        public void onNext(List<HashtagBo> hashtagBos) {
            models = HashtagModelMapper.toModel(hashtagBos);
        }
    }
}