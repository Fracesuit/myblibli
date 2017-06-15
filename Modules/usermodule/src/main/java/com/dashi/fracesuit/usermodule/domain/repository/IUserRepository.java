package com.dashi.fracesuit.usermodule.domain.repository;


import com.dashi.fracesuit.usermodule.modle.User;

import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public interface IUserRepository {
    Observable<User> getUser();
}
