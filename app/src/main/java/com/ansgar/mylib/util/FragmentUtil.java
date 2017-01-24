package com.ansgar.mylib.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by kirill on 24.1.17.
 */

public class FragmentUtil {

    public static void replaceFragment(FragmentActivity activity, int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public static void clearBackStack(FragmentActivity activity, int count){
        FragmentManager manager = activity.getSupportFragmentManager();
        for(int i = 0; i < count; i++){
            manager.popBackStack();
        }
    }

}
