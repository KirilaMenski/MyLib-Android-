package com.ansgar.mylib.ui.view.activity;

import com.ansgar.mylib.ui.base.BaseContextView;

/**
 * Created by kirill on 25.1.17.
 */
public interface MainActivityView extends BaseContextView {

    void setUserName(String name);

    void setUserAvatar(String avatar);

    void setFooterVis(boolean vis, int pos);

}
