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

import android.util.Log;

import com.mgdiez.domain.bean.StatisticBo;
import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.interactor.statistics.GetHashtagsStatisticsUseCase;
import com.mgdiez.domain.repository.StatisticsRepository;
import com.mgdiez.tweetstat.UIThread;
import com.mgdiez.tweetstat.model.StatisticModel;
import com.mgdiez.tweetstat.model.mapper.StatisticModelMapper;
import com.mgdiez.tweetstat.view.fragment.statistics.HashtagsStatisticsFragment;

import java.util.List;

import executor.JobExecutor;
import repository.StatisticsRepositoryImpl;
import rx.Subscriber;

public class HashtagsStatisticsPresenter implements StatisticsPresenter {

    private static final String TAG = StatisticsRepositoryImpl.class.getName();

    private HashtagsStatisticsFragment view;

    private GetHashtagsStatisticsUseCase getHashtagsStatisticsUseCase;

    private List<StatisticModel> models;


    public HashtagsStatisticsPresenter(HashtagsStatisticsFragment hashtagsStatisticsFragment) {
        view = hashtagsStatisticsFragment;
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        StatisticsRepository statisticsRepository = new StatisticsRepositoryImpl(view.getContext());
        getHashtagsStatisticsUseCase = new GetHashtagsStatisticsUseCase(jobExecutor, postExecutionThread, statisticsRepository);

    }

    public void getHashtags() {
        getHashtagsStatisticsUseCase.execute(new GetHashtagsStatisticsSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getHashtagsStatisticsUseCase.unsubscribe();
    }

    private void setModels() {
        view.setItems(models);
    }

    private void showMessage(String message) {
        view.showMessage(message);
    }

    private class GetHashtagsStatisticsSubscriber extends Subscriber<List<StatisticBo>> {

        @Override
        public void onCompleted() {
            setModels();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            showMessage(e.getMessage());
        }

        @Override
        public void onNext(List<StatisticBo> statisticBo) {
            models = StatisticModelMapper.toModel(statisticBo);
            onCompleted();
        }
    }
}
