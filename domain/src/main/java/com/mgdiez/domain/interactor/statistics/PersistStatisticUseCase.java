package com.mgdiez.domain.interactor.statistics;

import com.mgdiez.domain.bean.StatisticBo;
import com.mgdiez.domain.executor.PostExecutionThread;
import com.mgdiez.domain.executor.ThreadExecutor;
import com.mgdiez.domain.interactor.UseCase;
import com.mgdiez.domain.repository.StatisticsRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class PersistStatisticUseCase extends UseCase {

    private final StatisticsRepository statisticsRepository;

    private StatisticBo bo;

    @Inject
    public PersistStatisticUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                            StatisticsRepository statisticsRepository) {
        super(threadExecutor, postExecutionThread);
        this.statisticsRepository = statisticsRepository;
    }

    public void execute(StatisticBo bo, Subscriber useCaseSubscriber) {
        this.bo = bo;
        super.execute(useCaseSubscriber);

    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    @Override
    protected Observable buildUseCaseObservable() {
        return statisticsRepository.persistStatistic(bo);
    }
}
