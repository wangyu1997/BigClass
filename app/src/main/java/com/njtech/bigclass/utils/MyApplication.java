package com.njtech.bigclass.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.njtech.bigclass.utils.cache.SetCookieCache;
import com.njtech.bigclass.utils.persistence.SharedPrefsCookiePersistor;


public class MyApplication extends Application {
    static Context context;
    static int screenwidth = 1080;
    private static String token = "";
    static ClearableCookieJar cookieJar;



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();


    }

    public static void restartApp() {
        AppManager.getAppManager().AppExit(getGlobalContext());
        Intent i = getGlobalContext().getPackageManager()
                .getLaunchIntentForPackage(getGlobalContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getGlobalContext().startActivity(i);
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String t) {
        token = t;
    }

    public static Context getGlobalContext() {
        return context;
    }

    public static ClearableCookieJar getCookieJar() {
        if (cookieJar == null)

            return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        else
            return cookieJar;
    }

    public static int getWidth() {
        return screenwidth;
    }

    public static void setWidth(int width) {
        screenwidth = width;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("login", MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

}
