package com.zeroisbiggerthanone.pcs_aos.app;

import android.app.Application;

import com.zeroisbiggerthanone.pcs_aos.utils.SharedPreferencesManager;


public class MyApplication extends Application {

    private static MyApplication sInstance;

    private SharedPreferencesManager mSharedPreferencesManager;

    public static synchronized MyApplication getApplication() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public SharedPreferencesManager getPrefManager() {
        if (mSharedPreferencesManager == null) {
            mSharedPreferencesManager = new SharedPreferencesManager(this);
        }

        return mSharedPreferencesManager;
    }
}
