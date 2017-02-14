package com.ansgar.mylib.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

/**
 * Created by kirill on 13.2.17.
 */

public class FileManagerUtil {

    private static final String SD_PATH = "/MyLib";
    public static final String SD_BOOKS = "/booksCover";
    public static final String SD_AUTHORS = "/booksCover";
    public static final String SD_USERS = "/booksCover";

    public static String saveFile(Bitmap image, String fileName, String type) {
        String path;
        OutputStream os;

        File filepath = Environment.getExternalStorageDirectory();
        String fileDir = "";
        if (type.equals(SD_BOOKS)) {
            fileDir = SD_PATH + SD_BOOKS;
        }
        if (type.equals(SD_AUTHORS)) {
            fileDir = SD_PATH + SD_AUTHORS;
        }
        if (type.equals(SD_USERS)) {
            fileDir = SD_PATH + SD_USERS;
        }

        File dir = new File(filepath + fileDir);
        dir.mkdirs();
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
