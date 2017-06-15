package com.dashi.fracesuit.usermodule.data.repository;

import com.dashi.fracesuit.usermodule.data.net.UserApiService;
import com.dashi.fracesuit.usermodule.domain.repository.IUser2Repository;
import com.dashi.fracesuit.usermodule.modle.User2;

import rx.Observable;


/**
 * Created by Fracesuit on 2017/5/22.
 */

public class User2Repository implements IUser2Repository {


    @Override
    public Observable<User2> getUser2() {
        return UserApiService.instance().getUser2();
    }

}
