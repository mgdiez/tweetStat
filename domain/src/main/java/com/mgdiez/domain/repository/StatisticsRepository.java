package com.mgdiez.domain.repository;

import com.mgdiez.domain.bean.StatisticBo;

import java.util.List;

import rx.Observable;

public interface StatisticsRepository {

    Observable<List<StatisticBo>> getStatisticsTimeline();
    Observable<List<StatisticBo>> getStatisticsHomeTimeline();
    Observable<List<StatisticBo>> getStatisticsSearch();
    Observable<List<StatisticBo>> getStatisticsHashtags();
    Observable<StatisticBo> getStatisticById(long id);
    Observable<Void> persistStatistic(StatisticBo bo);
}
