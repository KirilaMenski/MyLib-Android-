package com.ansgar.mylib.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by kirill on 9.2.17.
 */

public class NetWorkUtils {

    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
