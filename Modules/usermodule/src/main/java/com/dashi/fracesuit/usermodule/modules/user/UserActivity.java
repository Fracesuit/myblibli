package com.dashi.fracesuit.usermodule.modules.user;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dashi.fracesuit.commonlibs.base.mvp.RxMvpBaseActivity;
import com.dashi.fracesuit.usermodule.R;
import com.dashi.fracesuit.usermodule.R2;
import com.dashi.fracesuit.usermodule.modle.User;
import com.dashi.fracesuit.usermodule.modle.User2;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UserActivity extends RxMvpBaseActivity<UserContract.UserView, UserPresenter> implements UserContract.UserView, View.OnClickListener {
    @BindView(R2.id.tv_name)
    TextView tvName;
    @BindView(R2.id.tv_age)
    TextView tvAge;

    @BindView(R2.id.btn_get)
    Button btnGet;
    @BindView(R2.id.btn_get2)
    Button btnGet2;
    @BindView(R2.id.btn_cancel)
    Button btnCncel;


    @Override
    protected void initListener() {
        btnGet.setOnClickListener(this);
        btnGet2.setOnClickListener(this);
        btnCncel.setOnClickListener(this);
    }


    @Override
    protected void initData() {

    }

    @Override
    public LifecycleTransformer bindLifecycle(Class clazz) {
        return this.bindUntilEvent(ActivityEvent.DESTROY);
    }

    @Override
    @LayoutRes
    public int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void getUserSuccess(User user) {
        tvName.setText(user.getName());
        tvAge.setText(user.getAge());
    }

    @Override
    public void getUser2Success(User2 user) {

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



}
