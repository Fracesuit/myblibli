package com.dashi.fracesuit.rxjava1x;

import android.app.Application;
import android.content.Context;

/**
 * Created by Fracesuit on 2017/6/13.
 */

public class RxjavaApplication {
    public static boolean debug = true;
    public static Context context;

    public static void init(Application application, Boolean isDebug) {
        debug = isDebug;
        context = application;
    }
}
