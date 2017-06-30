package com.dashi.fracesuit.commonlibs.base;


import com.dashi.fracesuit.rxjava1x.interator.BaseView;

public abstract class BasePresenter<V extends BaseView> {
    protected V mView;

    protected void attach(V view) {
        mView = view;
    }

    protected void destroy() {
        mView = null;
    }

}
