package com.dashi.fracesuit.commonlibs.base.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.dashi.fracesuit.commonlibs.CommApplication;
import com.dashi.fracesuit.commonlibs.utils.ToastUtils;
import com.dashi.fracesuit.logger.LogUtils;
import com.dashi.fracesuit.permissions.PermissionsHelp;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Activity基类
 */
public abstract class BaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends AppCompatActivity implements LifecycleProvider<ActivityEvent>, BaseView {

    private final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    @NonNull
    public T mPresenter;

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <P> LifecycleTransformer<P> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <P> LifecycleTransformer<P> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
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

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupMvp();
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG + "onCreate");
        lifecycleSubject.onNext(ActivityEvent.CREATE);
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

    protected abstract void init();

    protected abstract void requestPermissions();

    protected abstract void initViews(Bundle savedInstanceState);

    protected void initToolBar() {
    }

    protected abstract void initListener();

    protected abstract void initData();


    @Override
    public Context getContext() {
        return CommApplication.context;
    }

    //==========================等待框start==========================
    ProgressDialog mProgressDialog;

    protected void showProgressBar() {
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
        LogUtils.d(TAG + "cancelTask");
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

    //========================activity相关start===============================


    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
        LogUtils.d(TAG + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
        LogUtils.d(TAG + "onResume");
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
        mPresenter.onPause();
        LogUtils.d(TAG + "onPause");
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
        mPresenter.onStop();
        LogUtils.d(TAG + "onStop");
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        LogUtils.d(TAG + "onDestroy");
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        mPresenter.detachView();
        mPresenter.onDestroy();
        super.onDestroy();
    }

    //========================activity相关end===============================


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PermissionsHelp.PERMISSION_REQUEST_CODE) {
            requestPermissions();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
