package com.dashi.fracesuit.rxjava1x.interator;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * MVPPlugin
 */

public interface RxBaseView {
    void doOnStart(int requestCode);

    void doOnCancel(int requestCode);

    void doOnError(int requestCode, String msg);

    void onCompleted(int requestCode);

    LifecycleTransformer bindLifecycle(int requestCode);


}
