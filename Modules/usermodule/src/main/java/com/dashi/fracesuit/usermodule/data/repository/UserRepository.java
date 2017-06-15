package com.dashi.fracesuit.usermodule.data.repository;

import com.dashi.fracesuit.usermodule.data.net.UserApiService;
import com.dashi.fracesuit.usermodule.domain.repository.IUserRepository;
import com.dashi.fracesuit.usermodule.modle.User;

import rx.Observable;


/**
 * Created by Fracesuit on 2017/5/22.
 */

public class UserRepository implements IUserRepository {

    @Override
    public Observable<User> getUser() {
        return UserApiService.instance().getUser();
    }

}
