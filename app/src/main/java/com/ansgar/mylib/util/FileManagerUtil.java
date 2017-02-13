package com.ansgar.mylib.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by kirill on 13.2.17.
 */

public class FileManagerUtil {

    private static final String SD_PATH = "/MyLib/booksCover/";

    public static String saveFile(Bitmap image, String fileName) {
        String path;
        OutputStream os;

        File filepath = Environment.getExternalStorageDirectory();

        File dir = new File(filepath + SD_PATH);
        dir.mkdir();
        File file = new File(dir, fileName + ".png");

        try {
            os = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {

        }
        path = file.getPath();
        Log.i("!!!!!!!!!", "path: " + path);
        return path;
    }

}
