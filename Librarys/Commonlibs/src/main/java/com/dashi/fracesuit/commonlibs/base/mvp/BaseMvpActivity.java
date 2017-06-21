package com.dashi.fracesuit.commonlibs.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dashi.fracesuit.logger.LogUtils;

import java.lang.reflect.ParameterizedType;

/**
 * Activity基类
 */
public abstract class BaseMvpActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends BaseRxLifecycleActivity {

    @NonNull
    public T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupMvp();
    }

    private void setupMvp() {
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //========================activity相关start===============================
    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
        LogUtils.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
        LogUtils.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
        LogUtils.d("onStop");
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mPresenter.onDestroy();
        super.onDestroy();
    }
    //========================activity相关end===============================
}
