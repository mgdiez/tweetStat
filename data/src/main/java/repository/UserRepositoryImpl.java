package repository;

import android.content.Context;

import com.mgdiez.domain.bean.UserBo;
import com.mgdiez.domain.repository.UserRepository;

import repository.datasource.CloudUserDatastore;
import repository.datasource.LocalUserDatastore;
import rx.Observable;

public class UserRepositoryImpl implements UserRepository {


    private final Context context;

    public UserRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Observable<UserBo> getUserData(boolean hasConnection) {
        if (hasConnection) {
            return new CloudUserDatastore(context).getUserData();
        } else {
            return new LocalUserDatastore(context).getUserData();
        }
    }
}
