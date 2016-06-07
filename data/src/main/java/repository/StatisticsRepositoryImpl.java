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
        return null;
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsHomeTimeline() {
        return new LocalStatisticDatastore(context).getStatisticsHomeTimeline();
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsSearch() {
        return null;
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsHashtags() {
        return null;
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticById(long id) {
        return null;
    }

    @Override
    public Observable<Void> persistStatistic(StatisticBo bo) {
        return new LocalStatisticDatastore(context).persistStatistic(bo);
    }
}
