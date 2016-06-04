package repository.datasource;

import com.mgdiez.domain.bean.UserBo;

import rx.Observable;

public interface UserDatastore {

    Observable<UserBo> getUserData();

}
