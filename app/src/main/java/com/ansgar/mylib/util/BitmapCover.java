package com.ansgar.mylib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by kirill on 31.1.17.
 */
public class BitmapCover {

    public static Bitmap getBitmapCover(String cover) {
        InputStream inputStream = new ByteArrayInputStream(Base64.decode(cover.getBytes(), Base64.DEFAULT));
        return BitmapFactory.decodeStream(inputStream);
    }

    public static String getStringBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

}
