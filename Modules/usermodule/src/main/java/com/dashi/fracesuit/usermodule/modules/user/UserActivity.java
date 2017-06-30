package com.dashi.fracesuit.usermodule.modules.user;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dashi.fracesuit.commonlibs.base.BaseActivity;
import com.dashi.fracesuit.rxjava1x.interator.BaseView;
import com.dashi.fracesuit.usermodule.R;
import com.dashi.fracesuit.usermodule.R2;

import butterknife.BindView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UserActivity extends BaseActivity<BaseView, UserPresenter> implements View.OnClickListener {
    @BindView(R2.id.tv_name)
    TextView tvName;

    @BindView(R2.id.tv_age)
    public TextView tvAge;

    @BindView(R2.id.btn_get)
    Button btnGet;
    @BindView(R2.id.btn_get2)
    Button btnGet2;
    @BindView(R2.id.btn_cancel)
    Button btnCncel;

    @BindView(R2.id.btn_cancel1)
    Button btnCncel1;


    @Override
    protected void initListener() {
        btnGet.setOnClickListener(this);
        btnGet2.setOnClickListener(this);
        btnCncel.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        btnCncel1.setText("dddef");
    }


    @Override
    @LayoutRes
    public int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void requestPermissions() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolBar() {
        toolbar.supportBack();
    }

    @Override
    protected void cancelTask() {
        super.cancelTask();
        mPresenter.cancel();
    }

    @Override
    public void onClick(View v) {
        toast("click");
        int i = v.getId();
        if (i == R.id.btn_get) {
            mPresenter.get();
        } else if (i == R.id.btn_get2) {
            mPresenter.get2();
        } else if (i == R.id.btn_cancel)

        {
            mPresenter.cancel();
        }
    }

    @Override
    public void doParseData(int requestCode, Object data) {

    }
}
