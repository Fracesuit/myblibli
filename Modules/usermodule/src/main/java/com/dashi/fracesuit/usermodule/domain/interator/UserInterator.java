package com.dashi.fracesuit.usermodule.domain.interator;


import com.dashi.fracesuit.rxjava1x.interator.BaseInteractor;
import com.dashi.fracesuit.usermodule.domain.repository.IUserRepository;
import com.dashi.fracesuit.usermodule.modle.User;

import rx.Observable;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public class UserInterator extends BaseInteractor<User, Void> {
    IUserRepository userRepository;

    public UserInterator(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<User> buildObservable(Void param) {
        return userRepository.getUser();
    }
}
