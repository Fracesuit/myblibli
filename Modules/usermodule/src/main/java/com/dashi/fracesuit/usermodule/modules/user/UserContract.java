package com.dashi.fracesuit.usermodule.modules.user;

import com.dashi.fracesuit.commonlibs.base.mvp.BasePresenter;
import com.dashi.fracesuit.commonlibs.base.mvp.BaseView;
import com.dashi.fracesuit.usermodule.modle.User;
import com.dashi.fracesuit.usermodule.modle.User2;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UserContract {
    public interface UserView extends BaseView {
        void getUserSuccess(User user);
        void getUser2Success(User2 user);
    }

    public interface Presenter extends BasePresenter<UserView> {
            void get2();
    }
}
