package com.dashi.fracesuit.leakcanary;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public class LeakcanaryApplication {
    public static void init(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(application);
    }
}
