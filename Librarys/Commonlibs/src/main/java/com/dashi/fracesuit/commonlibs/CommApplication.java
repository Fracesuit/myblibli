package com.dashi.fracesuit.commonlibs;

import android.app.Application;
import android.content.Context;

import com.dashi.fracesuit.logger.LoggerApplication;
import com.dashi.fracesuit.retrofit2.RetrofitApplication;
import com.dashi.fracesuit.retrofit2.RetrofitHelper;
import com.dashi.fracesuit.rxjava1x.RxjavaApplication;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public class CommApplication {
    public static RetrofitHelper retrofitHelper;
    public static Context context;

    public static void init(Application application, Boolean isDebug) {
        context = application;

        //网络
        retrofitHelper = RetrofitApplication.init(application, isDebug, null);
        RxjavaApplication.init(application, isDebug);

        //日志
        LoggerApplication.init("CommonLog", isDebug);
    }
}
