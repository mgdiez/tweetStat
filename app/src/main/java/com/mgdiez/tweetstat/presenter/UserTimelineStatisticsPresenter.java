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
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.interactor.statistics.GetTimelineStatisticsUseCase;
import com.mgdiez.tweetstat.injector.PerActivity;
import com.mgdiez.tweetstat.model.StatisticModel;
import com.mgdiez.tweetstat.model.mapper.StatisticModelMapper;
import com.mgdiez.tweetstat.view.fragment.statistics.UserTimelineStatisticsFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import repository.StatisticsRepositoryImpl;
import rx.Subscriber;

@PerActivity
public class UserTimelineStatisticsPresenter implements StatisticsPresenter {

    private static final String TAG = StatisticsRepositoryImpl.class.getName();

    private UserTimelineStatisticsFragment view;

    private GetTimelineStatisticsUseCase getTimelineStatisticsUseCase;

    private List<StatisticModel> models;

    @Inject
    public UserTimelineStatisticsPresenter(@Named("getTimelineUseStatisticsUseCase") UseCase
                                                       getTimelineStatisticsUseCase) {
        this.getTimelineStatisticsUseCase = (GetTimelineStatisticsUseCase)
                getTimelineStatisticsUseCase;
    }


    public void getTimelineStatistics() {
        getTimelineStatisticsUseCase.execute(new GetTimelineStatisticsSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getTimelineStatisticsUseCase.unsubscribe();
    }

    private void setModels() {
        view.setItems(models);
    }

    private void showMessage(String message) {
        view.showMessage(message);
    }

    public void setView(UserTimelineStatisticsFragment view) {
        this.view = view;
    }

    public UserTimelineStatisticsFragment getView() {
        return view;
    }

    private class GetTimelineStatisticsSubscriber extends Subscriber<List<StatisticBo>> {

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
