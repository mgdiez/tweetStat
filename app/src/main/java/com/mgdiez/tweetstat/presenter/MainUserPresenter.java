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

import com.mgdiez.domain.bean.UserBo;
import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.interactor.tweets.GetTimelineUseCase;
import com.mgdiez.domain.interactor.user.GetUserDataUseCase;
import com.mgdiez.domain.repository.UserRepository;
import com.mgdiez.tweetstat.UIThread;
import com.mgdiez.tweetstat.model.UserModel;
import com.mgdiez.tweetstat.model.mapper.UserModelMapper;
import com.mgdiez.tweetstat.view.activity.MainActivity;

import javax.inject.Inject;
import javax.inject.Named;

import executor.JobExecutor;
import repository.UserRepositoryImpl;
import rx.Subscriber;

public class MainUserPresenter implements UserPresenter {

    private static final String TAG = HomeTimelineTweetsPresenter.class.getName();

    private MainActivity view;
    private GetUserDataUseCase getUserDataUseCase;
    private UserModel userModel;
/*
    public MainUserPresenter(MainActivity mainActivity) {
        view = mainActivity;
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        UserRepository userRepository = new UserRepositoryImpl(view);
        getUserDataUseCase = new GetUserDataUseCase(jobExecutor, postExecutionThread,
                userRepository);
    }*/

    @Inject
    public MainUserPresenter(@Named("getUserDataUseCase") UseCase
                                         getUserDataUseCase) {
        this.getUserDataUseCase = (GetUserDataUseCase) getUserDataUseCase;
    }

    public void setView(@NonNull MainActivity mainActivity) {
        this.view = mainActivity;
    }

    public void setUser() {
        view.setUser(userModel);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getUserDataUseCase.unsubscribe();
    }

    @Override
    public void getUserData(boolean hasConnection) {
        getUserDataUseCase.execute(hasConnection, new GetUserDataSubscriber());
    }


    private class GetUserDataSubscriber extends Subscriber<UserBo> {

        @Override
        public void onCompleted() {
            setUser();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(UserBo user) {
            userModel = UserModelMapper.toModel(user);
            onCompleted();
        }
    }
}
