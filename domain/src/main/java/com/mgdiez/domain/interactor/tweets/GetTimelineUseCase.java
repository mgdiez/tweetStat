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
package com.mgdiez.domain.interactor.tweets;


import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.repository.TweetsRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * UseCase that retrieves the User Timeline Tweets
 */
public class GetTimelineUseCase extends UseCase {

    private final TweetsRepository tweetsRepository;

    private boolean refresh;

    private String userName;

    @Inject
    public GetTimelineUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                              TweetsRepository tweetsRepository) {
        super(threadExecutor, postExecutionThread);
        this.tweetsRepository = tweetsRepository;
    }

    public void execute(boolean refresh, String userName, Subscriber useCaseSubscriber) {
        this.refresh = refresh;
        this.userName = userName;
        super.execute(useCaseSubscriber);

    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    protected Observable buildUseCaseObservable() {
        return tweetsRepository.getTweetsTimeline(refresh, userName);
    }
}
