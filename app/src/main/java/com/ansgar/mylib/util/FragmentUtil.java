package com.ansgar.mylib.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    static public void replaceAnimFragment(FragmentActivity activity, int containerId, Fragment fragment, boolean addToBackStack, int animStart, int animEnd) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.setCustomAnimations(R.anim.right_out, R.anim.left_out);
        ft.setCustomAnimations(animStart, animEnd);
        ft.replace(containerId, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public static void clearBackStack(FragmentActivity activity, int count) {
        FragmentManager manager = activity.getSupportFragmentManager();
        for (int i = 0; i < count; i++) {
            manager.popBackStack();
        }
        hideKeyBoard(activity);
    }

    private static void hideKeyBoard(FragmentActivity fragmentActivity) {
        Activity activity = fragmentActivity;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

}
