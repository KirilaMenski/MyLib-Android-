package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;

/**
 * Created by kirill on 24.1.17.
 */
public interface ProfileFragmentView extends BaseContextView {

    void setScreenTitle(String title);

    void setUserAvatar(String avatar);

    void setBookCount(String count);

    void setAuthorCount(String count);

    void setUserName(String name);

    void setUserEmail(String email);

}
