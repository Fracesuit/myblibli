package com.dashi.fracesuit.commonlibs.base;


import com.dashi.fracesuit.rxjava1x.interator.RxBaseView;

public abstract class BasePresenter<V extends RxBaseView> {
    protected V mView;

    protected void attach(V view) {
        mView = view;
    }

    protected void destroy() {
        mView = null;
    }

}
