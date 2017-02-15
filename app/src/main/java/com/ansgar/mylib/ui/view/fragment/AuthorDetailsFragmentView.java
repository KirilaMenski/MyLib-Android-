package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;

import java.io.File;

/**
 * Created by kirill on 27.1.17.
 */
public interface AuthorDetailsFragmentView extends BaseContextView {
    void setScreenTitle(String title);

    void setAuthorImage(File authorIcon);

    void setAuthorName(String name);

    void setAuthorBiography(String biography);
}
