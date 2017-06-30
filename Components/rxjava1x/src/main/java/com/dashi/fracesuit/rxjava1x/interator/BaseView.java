package com.dashi.fracesuit.rxjava1x.interator;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * MVPPlugin
 */

public interface BaseView {
    void doOnStart(int requestCode);

    void doOnCancel(int requestCode);

    void doOnError(int requestCode, String msg);

    void doOnCompleted(int requestCode);

    LifecycleTransformer doBindLifecycle(int requestCode);

    void doParseData(int requestCode, Object data);
}
