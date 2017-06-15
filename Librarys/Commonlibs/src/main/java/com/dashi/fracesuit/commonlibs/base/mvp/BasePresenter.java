package com.dashi.fracesuit.commonlibs.base.mvp;


public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
