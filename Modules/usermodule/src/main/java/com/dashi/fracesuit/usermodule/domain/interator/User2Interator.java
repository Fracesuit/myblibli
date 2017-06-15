package com.dashi.fracesuit.usermodule.domain.interator;


import com.dashi.fracesuit.rxjava1x.interator.BaseInteractor;
import com.dashi.fracesuit.usermodule.domain.repository.IUser2Repository;
import com.dashi.fracesuit.usermodule.modle.User2;

import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public class User2Interator extends BaseInteractor<User2, Void> {
    IUser2Repository userRepository;

    public User2Interator(IUser2Repository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<User2> buildObservable(Void param) {
        return userRepository.getUser2();
    }
}
