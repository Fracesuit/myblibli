package com.dashi.fracesuit.usermodule.data.repository;

import com.dashi.fracesuit.usermodule.data.net.ApiService;
import com.dashi.fracesuit.usermodule.domain.repository.BaseRepository;
import com.dashi.fracesuit.usermodule.modle.User2;

import rx.Observable;


/**
 * Created by Fracesuit on 2017/5/22.
 */

public class User2Repository implements BaseRepository<User2, Void> {

    @Override
    public Observable<User2> requestData(Void aVoid) {
        return ApiService.instance().getUser2();
    }
}
