package com.dashi.fracesuit.usermodule.data.net;

import com.dashi.fracesuit.commonlibs.CommApplication;
import com.dashi.fracesuit.commonlibs.constant.CommContants;

/**
 * Created by Fracesuit on 2017/5/23.
 */

public class UserApiService {
    private static AppApi api;

    public static synchronized AppApi instance() {
        if (api == null) {
            api = CommApplication.retrofitHelper.createApi(AppApi.class, CommContants.BASE_URL);
        }
        return api;
    }
}
