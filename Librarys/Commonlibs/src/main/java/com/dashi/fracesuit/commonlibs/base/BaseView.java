package com.dashi.fracesuit.commonlibs.base;

import com.dashi.fracesuit.rxjava1x.interator.RxBaseView;

import java.util.List;

/**
 * Created by Fracesuit on 2017/6/30.
 */

public interface BaseView<T> extends RxBaseView {
    void parseData(T t);

    void parseDatas(List<T> datas);
}
