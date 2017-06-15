package com.dashi.fracesuit.rxjava1x.interator;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by AppleRen on 2017/3/27.
 */

public abstract class BaseInteractor<T, P> {
    private CompositeSubscription compositeSubscription;
    private Subscription subscribe;

    protected abstract Observable<T> buildObservable(P param);

    /**
     * 执行任务
     *
     * @param observer
     * @param param
     * @return
     */
    public Subscription execute(final DefaultSubscriber<T> observer, P param) {
        subscribe = buildObservable(param)
                .subscribeOn(Schedulers.io())//控制前面的
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        observer.doOnStart();//主线程中进行，也可以在子线程中进行，由后面的subscribeOn来确定
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())//控制后面的
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        observer.doOnCancel();//任务取消了
                    }
                })
                .compose(observer.bindLifecycle())//绑定生命周期
                .subscribe(observer);
        addSubscription(subscribe);
        return subscribe;
    }

    /**
     * 任务取消
     */
    public void cancel() {
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }


    /**
     * 释放资源
     */
    public void unCompositeSubscription() {
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }


    /**
     * 添加管理
     *
     * @param subscription
     */
    private void addSubscription(Subscription subscription) {
        if (compositeSubscription == null) compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(subscription);
    }


}
