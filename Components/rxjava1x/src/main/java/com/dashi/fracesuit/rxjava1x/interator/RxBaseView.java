package com.dashi.fracesuit.rxjava1x.interator;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * MVPPlugin
 */

public interface RxBaseView<T> {
    void doOnStart();

    void doOnCancel();

    void doOnError(String msg);

    void onCompleted();

    LifecycleTransformer bindLifecycle(Class clazz);
}
