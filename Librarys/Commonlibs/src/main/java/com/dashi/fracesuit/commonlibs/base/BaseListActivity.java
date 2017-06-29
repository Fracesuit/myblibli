package com.dashi.fracesuit.commonlibs.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.dashi.fracesuit.commonlibs.R;
import com.dashi.fracesuit.commonlibs.base.mvp.BaseActivity;
import com.dashi.fracesuit.commonlibs.base.mvp.BaseListView;
import com.dashi.fracesuit.commonlibs.base.mvp.BasePresenterImpl;
import com.dashi.fracesuit.commonlibs.base.mvp.BaseView;

/**
 * Created by Fracesuit on 2017/6/29.
 */

public class BaseListActivity<V extends BaseListView, T extends BasePresenterImpl<V>> extends BaseActivity<V, T> implements  BaseListView{

    @Override
    @LayoutRes
    protected int getLayoutId() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void requestPermissions() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
