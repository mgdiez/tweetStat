package repository.datasource;

import android.content.Context;

import com.mgdiez.data.bean.vo.tweet.UserVo;
import com.mgdiez.data.bean.vo.tweet.mapper.UserVoMapper;
import com.mgdiez.domain.bean.UserBo;

import io.realm.Realm;
import repository.RealmHelper;
import rx.Observable;

public class LocalUserDatastore implements UserDatastore {

    private Realm realm;

    public LocalUserDatastore(Context context) {
        this.realm = RealmHelper.getInstance(context);
    }

    @Override
    public Observable<UserBo> getUserData() {
        UserVo userVo = realm.where(UserVo.class).findFirst();

        if (userVo != null) {
            return Observable.just(UserVoMapper.toBo(userVo));
        }
        return Observable.empty();
    }
}
