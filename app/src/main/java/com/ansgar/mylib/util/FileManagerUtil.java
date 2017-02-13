package com.ansgar.mylib.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kirill on 13.2.17.
 */

public class FileManagerUtil {

    private static final String SD_PATH = "/MyLib/booksCover/";

    public static String saveFile(Activity activity, Bitmap image, String fileName) {
        String path = "";
        try {
            File file = new File(activity.getExternalFilesDir(null) + SD_PATH + fileName + ".png");
            path = Environment.getExternalStorageDirectory().toString();
            File newImage = new File(path + SD_PATH + fileName + ".png");
            FileOutputStream fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Log.i("!!!!!!!", "path: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

}
