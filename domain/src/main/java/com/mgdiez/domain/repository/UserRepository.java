package com.mgdiez.domain.repository;

import com.mgdiez.domain.bean.UserBo;

import rx.Observable;

/**
 * Created by Marc on 4/6/16.
 */
public interface UserRepository {
    Observable<UserBo> getUserData(boolean hasConnection);
}
