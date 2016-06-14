package com.mgdiez.domain.interactor.statistics;

import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.repository.StatisticsRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class GetHashtagsStatisticsUseCase extends UseCase {

    private final StatisticsRepository statisticsRepository;

    @Inject
    public GetHashtagsStatisticsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
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
        return statisticsRepository.getStatisticsHashtags();
    }
}
