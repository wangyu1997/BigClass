package com.njtech.bigclass.utils;

import java.io.File;
import java.util.concurrent.TimeUnit;


import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wangyu on 8/17/16.
 */

public class HttpControl {

    private volatile static HttpControl mInstance;
    private static Retrofit retrofit;
    private static OkHttpClient client;

    private HttpControl() {

//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheDirectory = new File(MyApplication.getGlobalContext()
                .getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);
        client = new OkHttpClient().newBuilder()
                .cookieJar(MyApplication.getCookieJar())
                .retryOnConnectionFailure(true)
                .cache(cache)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl((API.BASE_URL))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static HttpControl getInstance() {
        if (mInstance == null) {
            synchronized (HttpControl.class) {
                if (mInstance == null)
                    mInstance = new HttpControl();
            }
        }
        return mInstance;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            client = HttpControl.getInstance().initClient();
        }
        return client;

    }

    private OkHttpClient initClient() {
        return client;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = HttpControl.getInstance().initRetrofit();
        }
        return retrofit;
    }

    private Retrofit initRetrofit() {
        return retrofit;
    }
}
