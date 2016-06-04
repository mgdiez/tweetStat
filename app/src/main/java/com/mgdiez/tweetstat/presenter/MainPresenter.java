package com.mgdiez.tweetstat.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mgdiez.domain.bean.UserBo;
import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.interactor.GetUserDataUseCase;
import com.mgdiez.domain.repository.UserRepository;
import com.mgdiez.tweetstat.UIThread;
import com.mgdiez.tweetstat.model.UserModel;
import com.mgdiez.tweetstat.model.mapper.UserModelMapper;
import com.mgdiez.tweetstat.view.activity.MainActivity;

import executor.JobExecutor;
import repository.UserRepositoryImpl;
import rx.Subscriber;

/**
 * Created by Marc on 4/6/16.
 */
public class MainPresenter implements Presenter {

    private static final String TAG = HomeTimelinePresenter.class.getName();

    private MainActivity view;
    private GetUserDataUseCase getUserDataUseCase;
    private UserModel userModel;

    public MainPresenter(MainActivity mainActivity) {
        view = mainActivity;
        JobExecutor jobExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        UserRepository userRepository = new UserRepositoryImpl(view);
        getUserDataUseCase = new GetUserDataUseCase(jobExecutor, postExecutionThread, userRepository);
    }

    public void setView(@NonNull MainActivity mainActivity) {
        this.view = mainActivity;
    }

    public void setUser(){
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
