package com.dashi.fracesuit.logger;

import com.orhanobut.logger.Logger;

/**
 * Created by AppleRen on 2017/3/30.
 */

public class LogUtils {

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
