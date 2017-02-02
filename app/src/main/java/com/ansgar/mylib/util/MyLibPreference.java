package com.ansgar.mylib.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kirill on 24.1.17.
 */

public class MyLibPreference {

    private static final int LAST_ADDED = 1;
    public static final String SORT_TYPE_AUTHOR = "AUTHOR";
    public static final String SORT_TYPE_BOOK = "BOOK";

    public static final String BEL_LANG = "Беларуская";
    public static final String RUS_LANG = "Русский";
    public static final String ENG_LANG = "English";

    private static final String DEFAULT_STRING_VALUE = "";
    private static final int DEFAULT_USER = -1;

    private static final String CURRENT_USER = "current_user";
    private static final String CURRENT_LANG = "current_language";

    private static SharedPreferences mPreference;
    private static MyLibPreference mInstance;
    WeakReference<Context> mContext;

    public MyLibPreference(Context context) {
        this.mContext = new WeakReference<>(context);
        this.mPreference = getPrefs(mContext.get());
    }

    public static SharedPreferences getPrefs(Context ctx) {
        if (mPreference == null) {
            return mPreference = ctx.getSharedPreferences("Prefs", MODE_PRIVATE);
        } else {
            return mPreference;
        }
    }

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (MyLibPreference.class) {
                if (mInstance == null) {
                    mInstance = new MyLibPreference(context);
                }
            }
        }
    }

    public static void saveUserId(long userId) {
        SharedPreferences.Editor ed = mPreference.edit();
        ed.putLong(CURRENT_USER, userId);
        ed.commit();
    }

    public static void removeUserId() {
        SharedPreferences.Editor ed = mPreference.edit();
        ed.putLong(CURRENT_USER, DEFAULT_USER);
        ed.commit();
    }

    public static long getUserId() {
        return mPreference.getLong(CURRENT_USER, DEFAULT_USER);
    }

    public static void saveAuthorSortType(int type) {
        SharedPreferences.Editor ed = mPreference.edit();
        ed.putInt(SORT_TYPE_AUTHOR, type);
        ed.commit();
    }

    public static void removeAuthorSortType() {
        SharedPreferences.Editor ed = mPreference.edit();
        ed.putInt(SORT_TYPE_AUTHOR, LAST_ADDED);
        ed.commit();
    }

    public static int getAuthorSortType() {
        return mPreference.getInt(SORT_TYPE_AUTHOR, LAST_ADDED);
    }

    public static void saveBookSortType(int type) {
        SharedPreferences.Editor ed = mPreference.edit();
        ed.putInt(SORT_TYPE_BOOK, type);
        ed.commit();
    }

    public static void removeBookSortType() {
        SharedPreferences.Editor ed = mPreference.edit();
        ed.putInt(SORT_TYPE_BOOK, LAST_ADDED);
        ed.commit();
    }

    public static int getBookSortType() {
        return mPreference.getInt(SORT_TYPE_BOOK, LAST_ADDED);
    }

    public static void saveCurrentLang(String lang) {
        SharedPreferences.Editor ed = mPreference.edit();
        ed.putString(CURRENT_LANG, lang);
        ed.commit();
    }

    public static String getCurrentLang() {
        return mPreference.getString(CURRENT_LANG, ENG_LANG);
    }

    public static void removeCurrentLang() {
        SharedPreferences.Editor ed = mPreference.edit();
        ed.putString(CURRENT_LANG, ENG_LANG);
        ed.commit();
    }

    public static void clearData() {
        removeUserId();
        removeAuthorSortType();
        removeBookSortType();
    }

}
