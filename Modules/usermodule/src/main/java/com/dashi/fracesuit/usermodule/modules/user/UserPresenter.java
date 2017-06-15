package com.dashi.fracesuit.usermodule.modules.user;

import com.dashi.fracesuit.commonlibs.base.mvp.BasePresenterImpl;
import com.dashi.fracesuit.rxjava1x.interator.DefaultSubscriber;
import com.dashi.fracesuit.usermodule.data.repository.User2Repository;
import com.dashi.fracesuit.usermodule.data.repository.UserRepository;
import com.dashi.fracesuit.usermodule.domain.interator.User2Interator;
import com.dashi.fracesuit.usermodule.domain.interator.UserInterator;
import com.dashi.fracesuit.usermodule.modle.User;
import com.dashi.fracesuit.usermodule.modle.User2;


/**
 * MVPPlugin
 */

public class UserPresenter extends BasePresenterImpl<UserContract.UserView> implements UserContract.Presenter {
    private UserInterator userInterator;
    private User2Interator user2Interator;

    public UserPresenter() {
        userInterator = new UserInterator(new UserRepository());
        user2Interator = new User2Interator(new User2Repository());
    }

    public void cancel() {
        userInterator.cancel();
        user2Interator.cancel();
    }

    public void get() {
        userInterator.execute(new DefaultSubscriber<User>(mView) {
            @Override
            public void onNext(User user) {
                mView.getUserSuccess(user);
            }
        }, null);
    }

    @Override
    public void get2() {
        user2Interator.execute(new DefaultSubscriber<User2>(mView) {
            @Override
            public void onNext(User2 user2) {

            }
        }, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        user2Interator.unCompositeSubscription();
        userInterator.unCompositeSubscription();
    }
}
