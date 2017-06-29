package com.dashi.fracesuit.commonlibs.base.mvp;

import com.dashi.fracesuit.rxjava1x.interator.RxBaseView;

import java.util.List;

public interface BaseListView<T> extends RxBaseView<T> {
    void parseDatas(List<T> list);
}
