package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;

/**
 * Created by kirill on 25.1.17.
 */
public interface AddAuthorFragmentView extends BaseContextView {
    void setScreenTitle(String title);

    void setAuthorImage(String img);

    void setAuthorFirstName(String firstName);

    void setAuthorLastName(String lastName);

    void setAuthorBiography(String biography);

    void setAuthorDate(String date);
}
