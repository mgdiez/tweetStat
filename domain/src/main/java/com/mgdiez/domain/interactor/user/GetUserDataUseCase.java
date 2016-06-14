package com.mgdiez.domain.interactor.user;

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class GetUserDataUseCase extends UseCase {

    private UserRepository userRepository;

    private boolean hasConnection;

    @Inject
    public GetUserDataUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, UserRepository userRepository) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return userRepository.getUserData(hasConnection);
    }

    public void execute(boolean hasConnection, Subscriber UseCaseSubscriber) {
        this.hasConnection = hasConnection;
        super.execute(UseCaseSubscriber);
    }
}
