package repository.datasource;

import android.content.Context;

import com.mgdiez.data.bean.vo.tweet.StatisticVo;
import com.mgdiez.data.bean.vo.tweet.mapper.StatisticVoMapper;
import com.mgdiez.domain.bean.StatisticBo;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import repository.RealmHelper;
import rx.Observable;

public class LocalStatisticDatastore implements StatisticDatastore {

    private Realm realm;

    public LocalStatisticDatastore(Context context) {
        this.realm = RealmHelper.getInstance(context);
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsTimeline() {
        RealmResults<StatisticVo> statisticVos = realm.where(StatisticVo.class).equalTo(StatisticVo.TYPE,"TIMELINE").findAll();
        List<StatisticBo> statisticBos = StatisticVoMapper.toBo(statisticVos);

        if (statisticBos != null && !statisticBos.isEmpty()) {
            return Observable.just(statisticBos);
        }
        return Observable.empty();
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsHomeTimeline() {
        RealmResults<StatisticVo> statisticVos = realm.where(StatisticVo.class).equalTo(StatisticVo.TYPE,"MY_TWEETS").findAll();
        List<StatisticBo> statisticBos = StatisticVoMapper.toBo(statisticVos);

        if (statisticBos != null && !statisticBos.isEmpty()) {
            return Observable.just(statisticBos);
        }
        return Observable.empty();
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsSearch() {
        RealmResults<StatisticVo> statisticVos = realm.where(StatisticVo.class).equalTo(StatisticVo.TYPE,"SEARCH").findAll();
        List<StatisticBo> statisticBos = StatisticVoMapper.toBo(statisticVos);

        if (statisticBos != null && !statisticBos.isEmpty()) {
            return Observable.just(statisticBos);
        }
        return Observable.empty();
    }

    @Override
    public Observable<List<StatisticBo>> getStatisticsHashtags() {
        RealmResults<StatisticVo> statisticVos = realm.where(StatisticVo.class).equalTo(StatisticVo.TYPE,"HASHTAGS").findAll();
        List<StatisticBo> statisticBos = StatisticVoMapper.toBo(statisticVos);

        if (statisticBos != null && !statisticBos.isEmpty()) {
            return Observable.just(statisticBos);
        }
        return Observable.empty();
    }

    @Override
    public Observable<StatisticBo> getStatisticById(long id) {
        StatisticVo statisticVo = realm.where(StatisticVo.class).equalTo(StatisticVo.ID, id).findFirst();
        StatisticBo statisticBo = StatisticVoMapper.toBo(statisticVo);

        if (statisticBo != null) {
            return Observable.just(statisticBo);
        }
        return Observable.empty();
    }

    @Override
    public Observable<Void> persistStatistic(StatisticBo bo) {
            StatisticVo vo = StatisticVoMapper.toVo(bo);
            //int nextID = ((int) (realm.where(StatisticVo.class).max(StatisticVo.ID)))+1;

            vo.setId(new Random().nextInt());

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(vo);
            realm.commitTransaction();

            realm.close();

        return Observable.empty();
    }
}
