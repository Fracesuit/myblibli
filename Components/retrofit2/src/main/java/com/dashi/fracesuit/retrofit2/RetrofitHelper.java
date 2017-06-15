package com.dashi.fracesuit.retrofit2;

import android.content.Context;

import com.dashi.fracesuit.retrofit2.utils.NetUtils;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by Fracesuit on 2017/5/22.
 */

public class RetrofitHelper {
    HttpLoggingInterceptor loggingInterceptor;
    CacheInterceptor cacheInterceptor;
    Cache cache;
    StethoInterceptor stethoInterceptor;

    OkHttpClient mOkHttpClient = null;

    boolean loggingInterceptorFlag;
    boolean cacheInterceptorFlag;
    boolean stethoInterceptorFlag;
    boolean cacheFlag;


    private RetrofitHelper(Builder builder) {
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        stethoInterceptor = new StethoInterceptor();
        if (builder.context != null) {
            cache = new Cache(new File(builder.context.getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
            cacheInterceptor = new CacheInterceptor(builder.context);
        }
        //如果开启缓存拦截,就必须要设置缓存位置
        cacheFlag = builder.cacheInterceptorFlag ? builder.cacheInterceptorFlag : builder.cacheFlag;
        loggingInterceptorFlag = builder.loggingInterceptorFlag;
        cacheInterceptorFlag = builder.cacheInterceptorFlag;
        stethoInterceptorFlag = builder.stethoInterceptorFlag;
        inittOkhttpClient();
    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    public <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private synchronized void inittOkhttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            builder = cacheFlag && cache != null ? builder.cache(cache) : builder;
            builder = loggingInterceptorFlag && loggingInterceptor != null ? builder.addInterceptor(loggingInterceptor) : builder;
            builder = cacheInterceptorFlag && cacheInterceptor != null ? builder.addNetworkInterceptor(cacheInterceptor) : builder;
            builder = stethoInterceptorFlag && stethoInterceptor != null ? builder.addNetworkInterceptor(stethoInterceptor) : builder;
            mOkHttpClient = builder.build();
        }
    }


    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", ConstantsNet.COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private class CacheInterceptor implements Interceptor {
        Context context;

        CacheInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            //int maxAge = 60 * 60;
            int maxAge = 30;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (NetUtils.isNetworkAvailable(context)) {
                //有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetUtils.isNetworkAvailable(context)) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }


    public static final class Builder {
        private boolean cacheFlag = true;
        private boolean stethoInterceptorFlag = true;
        private boolean cacheInterceptorFlag = true;
        private boolean loggingInterceptorFlag = BuildConfig.DEBUG;
        private Context context;

        public Builder(Context cxt) {
            context = cxt;
        }

        public Builder cacheFlag(boolean val) {
            cacheFlag = val;
            return this;
        }

        public Builder stethoInterceptorFlag(boolean val) {
            stethoInterceptorFlag = val;
            return this;
        }

        public Builder cacheInterceptorFlag(boolean val) {
            cacheInterceptorFlag = val;
            return this;
        }

        public Builder loggingInterceptorFlag(boolean val) {
            loggingInterceptorFlag = val;
            return this;
        }

        public RetrofitHelper build() {
            return new RetrofitHelper(this);
        }
    }
}
