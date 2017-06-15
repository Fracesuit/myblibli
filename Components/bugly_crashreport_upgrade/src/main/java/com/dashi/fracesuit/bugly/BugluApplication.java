package com.dashi.fracesuit.bugly;

/**
 * Created by Fracesuit on 2017/6/13.
 */

import android.content.Context;

import com.tencent.bugly.Bugly;

/**
 * https://bugly.qq.com/
 * https://bugly.qq.com/docs/user-guide/instruction-manual-android-upgrade/?v=20170612101914
 */
public class BugluApplication {
    public static void init(Context context, Boolean isDebug) {
        // Bugly.init(context, "注册时申请的APPID", false);
        String appid = "52bd34c3f5";
        Bugly.init(context, appid, isDebug);
    }
}
