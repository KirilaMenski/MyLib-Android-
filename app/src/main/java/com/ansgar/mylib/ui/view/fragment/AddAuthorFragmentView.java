package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by kirill on 25.1.17.
 */
public interface AddAuthorFragmentView extends BaseContextView {
    void setScreenTitle(String title);

    void setAuthorImage(Bitmap img);

    void setAuthorFirstName(String firstName);

    void setAuthorLastName(String lastName);

    void setAuthorBiography(String biography);

    void setCoverPath(String path);

    void setAuthorDate(String date);

    void updatePhotoView(File path);

    void startActivityForResult(Intent intent);
}
