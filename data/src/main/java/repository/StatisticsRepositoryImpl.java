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
package repository;

import android.content.Context;

import com.mgdiez.domain.bean.StatisticBo;
import com.mgdiez.domain.repository.StatisticsRepository;

import java.util.List;

import javax.inject.Inject;

import repository.datasource.LocalStatisticDatastore;
import rx.Observable;

public class StatisticsRepositoryImpl implements StatisticsRepository {

    @Inject
    public Context context;

    //private LocalStatisticDatastore localStatisticDatastore;

    @Inject
    public StatisticsRepositoryImpl(/*Context context*/) {
        //localStatisticDatastore = new LocalStatisticDatastore(context);
    }

    /**
     *
     * @return Observable that contains the Statistics List done by Timeline
     */
    @Override
    public Observable<List<StatisticBo>> getStatisticsTimeline() {
        return new LocalStatisticDatastore(context).getStatisticsTimeline();
    }

    /**
     *
     * @return Observable that contains the Statistics List done by Home Timeline
     */
    @Override
    public Observable<List<StatisticBo>> getStatisticsHomeTimeline() {
        return new LocalStatisticDatastore(context).getStatisticsHomeTimeline();
    }

    /**
     *
     * @return Observable that contains the Statistics List done by Search
     */
    @Override
    public Observable<List<StatisticBo>> getStatisticsSearch() {
        return new LocalStatisticDatastore(context).getStatisticsSearch();
    }

    /**
     *
     * @return Observable that contains the Statistics List done by Trending Topics
     */
    @Override
    public Observable<List<StatisticBo>> getStatisticsHashtags() {
        return new LocalStatisticDatastore(context).getStatisticsHashtags();
    }

    /**
     *
     * @param id of the statistic required.
     * @return Observable that contains the Statistic.
     */
    @Override
    public Observable<StatisticBo> getStatisticById(long id) {
        return new LocalStatisticDatastore(context).getStatisticById(id);
    }

    /**
     *
     * @param bo to be saved in database.
     * @return Void as an observable.
     */
    @Override
    public Observable<Void> persistStatistic(StatisticBo bo) {
        return new LocalStatisticDatastore(context).persistStatistic(bo);
    }
}
