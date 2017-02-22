package com.ansgar.mylib.util;

import java.util.Locale;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;

/**
 * Created by kirill on 2.2.17.
 */

public class LanguageUtil {

    public static final Locale ENG = new Locale("en", "US");
    public static final Locale RUS = new Locale("ru", "RU");
    public static final Locale BEL = new Locale("be", "BY");

    public static void setLanguage(Context context, Locale locale) {
        Configuration conf = context.getResources().getConfiguration();
        conf.setLocale(locale);
        Locale.setDefault(locale);
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
    }

    public static ContextWrapper setLang(Context context, Locale locale) {
        Configuration conf = context.getResources().getConfiguration();
        conf.setLocale(locale);
        Locale.setDefault(locale);
        context = context.createConfigurationContext(conf);
        return new ContextWrapper(context);
    }

    public static void setCurrentLang(Context context) {
        if (MyLibPreference.getCurrentLang().equals(MyLibPreference.BEL_LANG)) {
            LanguageUtil.setLanguage(context, LanguageUtil.BEL);
        }
        if (MyLibPreference.getCurrentLang().equals(MyLibPreference.RUS_LANG)) {
            LanguageUtil.setLanguage(context, LanguageUtil.RUS);
        }
        if (MyLibPreference.getCurrentLang().equals(MyLibPreference.ENG_LANG)) {
            LanguageUtil.setLanguage(context, LanguageUtil.ENG);
        }
    }

}
