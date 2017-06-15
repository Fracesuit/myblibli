package com.dashi.fracesuit.myblibli;

import android.app.Application;

import com.dashi.fracesuit.bugly.BugluApplication;
import com.dashi.fracesuit.commonlibs.CommApplication;
import com.dashi.fracesuit.hotfix2.HotfixApplication;
import com.dashi.fracesuit.leakcanary.LeakcanaryApplication;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //公共模块初始化
        CommApplication.init(this, BuildConfig.DEBUG);

        //内存泄露
        LeakcanaryApplication.init(this);

        //热更新
        HotfixApplication.init(this, BuildConfig.VERSION_NAME, BuildConfig.DEBUG);

        //crash和自动更新
        BugluApplication.init(this, BuildConfig.DEBUG);
    }
}
