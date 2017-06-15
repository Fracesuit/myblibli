package com.dashi.fracesuit.usermodule.domain.repository;


import com.dashi.fracesuit.usermodule.modle.User2;

import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public interface IUser2Repository {
    Observable<User2> getUser2();
}
