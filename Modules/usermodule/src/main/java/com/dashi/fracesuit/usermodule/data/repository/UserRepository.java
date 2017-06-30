package com.dashi.fracesuit.usermodule.data.repository;

import com.dashi.fracesuit.usermodule.data.net.ApiService;
import com.dashi.fracesuit.usermodule.domain.repository.BaseRepository;
import com.dashi.fracesuit.usermodule.modle.User;

import rx.Observable;


/**
 * Created by Fracesuit on 2017/5/22.
 */

public class UserRepository implements BaseRepository<User, Void> {

    @Override
    public Observable<User> requestData(Void aVoid) {
        return ApiService.instance().getUser();
    }

}
