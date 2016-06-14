package repository;

import android.content.Context;

import com.mgdiez.domain.bean.StatisticBo;
import com.mgdiez.domain.repository.StatisticsRepository;

import java.util.List;

import repository.datasource.LocalStatisticDatastore;
import rx.Observable;

public class StatisticsRepositoryImpl implements StatisticsRepository {


    private final Context context;

    public StatisticsRepositoryImpl(Context context) {
        this.context = context;
    }


    @Override
    public Observable<List<StatisticBo>> getStatisticsTimeline() {
        return new LocalStatisticDatastore(context).getStatisticsTimeline();
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsHomeTimeline() {
        return new LocalStatisticDatastore(context).getStatisticsHomeTimeline();
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsSearch() {
        return new LocalStatisticDatastore(context).getStatisticsSearch();
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsHashtags() {
        return new LocalStatisticDatastore(context).getStatisticsHashtags();
    }

    @Override
    public Observable<StatisticBo> getStatisticById(long id) {
        return new LocalStatisticDatastore(context).getStatisticById(id);
    }

    @Override
    public Observable<Void> persistStatistic(StatisticBo bo) {
        return new LocalStatisticDatastore(context).persistStatistic(bo);
    }
}
