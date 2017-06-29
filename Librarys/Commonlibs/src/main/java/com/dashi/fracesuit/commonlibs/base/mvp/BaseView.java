package com.dashi.fracesuit.commonlibs.base.mvp;

import com.dashi.fracesuit.rxjava1x.interator.RxBaseView;


public interface BaseView<T> extends RxBaseView<T> {
    void parseData(T t);
}
