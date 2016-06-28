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
package com.mgdiez.domain.repository;

import com.mgdiez.domain.bean.StatisticBo;

import java.util.List;

import rx.Observable;

/**
 * Class that represents the Repository Pattern for Statistics Data
 */
public interface StatisticsRepository {

    /**
     *
     * @return Observable that contains the Statistics List done by Timeline
     */
    Observable<List<StatisticBo>> getStatisticsTimeline();
    /**
     *
     * @return Observable that contains the Statistics List done by Home Timeline
     */
    Observable<List<StatisticBo>> getStatisticsHomeTimeline();
    /**
     *
     * @return Observable that contains the Statistics List done by Search
     */
    Observable<List<StatisticBo>> getStatisticsSearch();
    /**
     *
     * @return Observable that contains the Statistics List done by Trending Topics
     */
    Observable<List<StatisticBo>> getStatisticsHashtags();
    /**
     *
     * @return Observable that contains the Statistics List done by Trending Topics
     */
    Observable<StatisticBo> getStatisticById(long id);
    /**
     *
     * @param bo to be saved in database.
     * @return Void as an observable.
     */
    Observable<Void> persistStatistic(StatisticBo bo);
}
