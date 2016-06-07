package com.mgdiez.domain.interactor;

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.repository.StatisticsRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class GetHomeTimelineStatisticsUseCase extends UseCase {

    private final StatisticsRepository statisticsRepository;

    @Inject
    public GetHomeTimelineStatisticsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                                   StatisticsRepository statisticsRepository) {
        super(threadExecutor, postExecutionThread);
        this.statisticsRepository = statisticsRepository;
    }

    public void execute(Subscriber useCaseSubscriber) {
        super.execute(useCaseSubscriber);

    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    protected Observable buildUseCaseObservable() {
        return statisticsRepository.getStatisticsHomeTimeline();
    }
}
