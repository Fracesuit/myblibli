package com.dashi.fracesuit.myblibli.main;

import com.dashi.fracesuit.commonlibs.base.mvp.BasePresenter;
import com.dashi.fracesuit.commonlibs.base.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */
//此处必须public,不然无法更新
//以后就一个原则,能public就public
public class MainContract {
    public interface MainView extends BaseView {

    }

    public interface  Presenter extends BasePresenter<MainView> {

    }
}
