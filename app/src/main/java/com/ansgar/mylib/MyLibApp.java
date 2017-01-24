package com.ansgar.mylib;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by kirill on 24.1.17.
 */

public class MyLibApp extends MultiDexApplication {

    private static Context sContext;

    public void onCreate() {
        super.onCreate();
        sContext = this;
//        MyLibPreferences.init(sContext);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return sContext;
    }

}
