package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;

/**
 * Created by kirill on 27.1.17.
 */
public interface AuthorDetailsFragmentView extends BaseContextView {
    void setScreenTitle(String title);

    void setAuthorImage(String img);

    void setAuthorName(String name);

    void setAuthorBiography(String biography);
}
