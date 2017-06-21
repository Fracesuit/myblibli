package com.dashi.fracesuit.commonlibs.base.mvp;

/**
 * Created by zhiren.zhang on 2017/6/21.
 * <p>
 * mvp模式，并且合并了rx生命周期。自动管理rxjava的生命周期
 * 后续还要集成dragger
 */

public abstract class BaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends BaseMvpAndRxLifecycleActivity<V, T > {
}
