package com.dashi.fracesuit.retrofit2;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import java.util.function.Consumer;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fracesuit on 2017/5/23.
 * <p>
 */

public class RetrofitApplication {
    public static RetrofitHelper init(Context context, Boolean isDebug, RetrofitHelper.Builder build) {
        if (isDebug) {
            Stetho.initializeWithDefaults(context);
        }
        if (build != null) {
            return build.build();
        } else {
            return new RetrofitHelper.Builder(context).build();
        }


    }
}
