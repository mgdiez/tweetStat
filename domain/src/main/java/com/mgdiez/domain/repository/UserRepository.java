package com.mgdiez.domain.repository;

import com.mgdiez.domain.bean.UserBo;

import rx.Observable;

public interface UserRepository {
    Observable<UserBo> getUserData(boolean hasConnection);
}
