package com.dashi.fracesuit.commonlibs.base.list;

import android.os.Bundle;

import com.dashi.fracesuit.commonlibs.R;
import com.dashi.fracesuit.commonlibs.base.BaseActivity;
import com.dashi.fracesuit.commonlibs.base.BasePresenter;
import com.dashi.fracesuit.commonlibs.base.BaseView;

/**
 * Created by Fracesuit on 2017/6/29.
 */

public abstract class BaseListActivity<DATA, T extends BaseListPresenter<DATA>> extends BaseActivity<BaseView<DATA>, BasePresenter<BaseView<DATA>>> implements BaseView {
    @Override
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
