package com.dashi.fracesuit.rxjava1x.interator;


import android.support.annotation.NonNull;

import com.dashi.fracesuit.rxjava1x.RxjavaApplication;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.lang.reflect.ParameterizedType;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by AppleRen on 2017/3/27.
 */

public abstract class DefaultSubscriber<T> extends Subscriber<T> {
    RxBaseView<T> mView;

    protected void doOnStart()//开始
    {
        mView.doOnStart();
    }

    protected void doOnCancel()//取消
    {
        mView.doOnCancel();
        mView = null;
    }

    protected LifecycleTransformer<T> bindLifecycle()//绑定生命周期,获取泛型的实际类型
    {
        Class<T> clazz = ((Class<T>) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
        return mView.bindLifecycle(clazz);
    }

    protected void doOnError(Throwable e, String msg)//错误的时候
    {
        mView.doOnError(msg);
    }

    @Override
    public void onCompleted() {
        mView.onCompleted();
    }


    public DefaultSubscriber(@NonNull RxBaseView view) {
        mView = view;
    }


    //Subscriber 的onstart不能更改线程，所以需要重写
    @Override
    public final void onError(Throwable e) {
        String error = null;
        if (e instanceof ConnectException) {
            error = "连接超时 请检查网络是否畅通";
        } else if (e instanceof UnknownHostException) {
            error = "未知主机错误 请检查网络是否畅通";
        } else if (e instanceof SocketTimeoutException) {
            error = "socket连接超时 请检查网络是否畅通";
        } else {
            error = "未知异常: " + e.getMessage();
        }

        if (!RxjavaApplication.debug) {
            error = "服务器正忙,请稍后再试";
        }
        doOnError(e, error);
    }
}
