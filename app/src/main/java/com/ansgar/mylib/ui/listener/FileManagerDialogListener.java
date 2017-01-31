package com.ansgar.mylib.ui.listener;

import java.io.InputStream;

/**
 * Created by kirill on 31.1.17.
 */

public interface FileManagerDialogListener {

    void fileSelected(InputStream inputStream, String path);

}
