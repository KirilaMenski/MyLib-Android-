package com.ansgar.mylib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by kirill on 31.1.17.
 */
public class BitmapCover {

    public static Bitmap getBitmapCover(String cover) {
        InputStream inputStream = new ByteArrayInputStream(Base64.decode(cover.getBytes(), Base64.DEFAULT));
        return BitmapFactory.decodeStream(inputStream);
    }

}
