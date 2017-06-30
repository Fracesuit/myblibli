package com.dashi.fracesuit.commonlibs.base.list;


import com.dashi.fracesuit.commonlibs.base.BasePresenter;
import com.dashi.fracesuit.rxjava1x.interator.BaseInteractor;
import com.dashi.fracesuit.rxjava1x.interator.RxBaseView;
import com.dashi.fracesuit.rxjava1x.interator.DefaultSubscriber;

import java.util.List;
import java.util.Map;

public abstract class BaseListPresenter<DATA> extends BasePresenter<RxBaseView<DATA>> {
    public void requestData(Map<String, String> params, int requestCode) {
        getInteractor().execute(new DefaultSubscriber<List<DATA>>(mView, requestCode) {
            @Override
            public void onNext(List<DATA> datas) {
                mView.parseDatas(datas);
            }
        }, params);

    }

    protected abstract BaseInteractor<List<DATA>, Map<String, String>> getInteractor();
}
