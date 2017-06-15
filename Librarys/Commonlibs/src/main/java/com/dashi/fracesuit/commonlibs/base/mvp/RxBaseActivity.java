package com.dashi.fracesuit.commonlibs.base.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;

import com.dashi.fracesuit.commonlibs.CommApplication;
import com.dashi.fracesuit.commonlibs.help.PermissionsHelp;
import com.dashi.fracesuit.commonlibs.utils.LogUtils;
import com.dashi.fracesuit.commonlibs.utils.ToastUtils;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 */
public abstract class RxBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends RxAppCompatActivity implements BaseView {

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化
        init();

        //动态权限
        requestPermissions();

        //初始化控件
        initViews(savedInstanceState);

        //初始化ToolBar
        initToolBar();

        //初始化监听
        initListener();

        //初始化数据
        initData();
    }


    @LayoutRes
    public abstract int getLayoutId();

    protected void init() {
    }

    public abstract String[] getPermissions();

    public abstract void initViews(Bundle savedInstanceState);

    public void initToolBar() {
    }

    protected abstract void initListener();

    protected abstract void initData();


    @Override
    public Context getContext() {
        return CommApplication.context;
    }

    //==========================等待框start==========================
    ProgressDialog mProgressDialog;

    public void showProgressBar() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, null, "请稍后...", true, true);
        }
        mProgressDialog.setMessage("请稍后...");
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelTask();
            }
        });
    }

    protected void cancelTask() {
    }

    public void hideProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }
    //==========================等待框end==========================


    //==========================Toast start==========================
    protected void toast(String msg) {
        ToastUtils.showShortToast(msg);
    }

    protected void toast(@StringRes int res) {
        ToastUtils.showShortToast(res);
    }

//==========================Toast end==========================


    //========================rx相关start===============================
    @Override
    public void doOnStart() {
        showProgressBar();
        LogUtils.d("任务开始了");
    }

    @Override
    public void doOnCancel() {
        hideProgressBar();
        // toast("任务取消了");
        LogUtils.d("任务取消了");
    }

    @Override
    public LifecycleTransformer bindLifecycle(Class clazz) {
        return this.bindToLifecycle();
    }


    @Override
    public void doOnError(String msg) {
        hideProgressBar();
        toast(msg);
        LogUtils.d("任务出现错误了");
    }

    @Override
    public void onCompleted() {
        hideProgressBar();
        LogUtils.d("任务完成了");
    }
    //========================rx相关end===============================

    @Override
    protected void onDestroy() {
        bind.unbind();
        LogUtils.d("onDestroy");
        super.onDestroy();

    }

    protected void requestPermissions() {
        String[] permissions = getPermissions();
        if (permissions != null && permissions.length > 0) {
            PermissionsHelp.requestPermissions(this,
                    null,
                    permissions);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PermissionsHelp.PERMISSION_REQUEST_CODE) {
            requestPermissions();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
