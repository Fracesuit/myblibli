package com.dashi.fracesuit.usermodule.domain.interator;


import com.dashi.fracesuit.rxjava1x.interator.BaseInteractor;
import com.dashi.fracesuit.usermodule.domain.repository.BaseRepository;
import com.dashi.fracesuit.usermodule.modle.User2;

import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public class User2Interator extends BaseInteractor<User2, Void> {
    BaseRepository userRepository;

    public User2Interator(BaseRepository user2Repository) {
        this.userRepository = user2Repository;
    }

    @Override
    protected Observable<User2> buildObservable(Void param) {
        return userRepository.requestData(param);
    }
}
