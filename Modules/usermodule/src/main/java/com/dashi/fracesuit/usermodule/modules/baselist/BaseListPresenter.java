package com.dashi.fracesuit.usermodule.modules.baselist;

import com.dashi.fracesuit.commonlibs.base.BasePresenter;
import com.dashi.fracesuit.rxjava1x.interator.BaseInteractor;
import com.dashi.fracesuit.rxjava1x.interator.BaseView;
import com.dashi.fracesuit.rxjava1x.interator.DefaultSubscriber;

import java.util.List;
import java.util.Map;


public abstract class BaseListPresenter<T> extends BasePresenter<BaseView> {

    public abstract BaseInteractor<List<T>, Map<String, String>> getInterator();

    protected void requestData(Map<String, String> params, final int requestCode) {
        getInterator().execute(new DefaultSubscriber<List<T>>(mView, requestCode) {
            @Override
            public void onNext(List<T> s) {
                mView.doParseData(requestCode, s);
            }
        }, params);

    }
}
