package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.SettingsFragmentView;

/**
 * Created by kirill on 2.2.17.
 */
public class SettingsFragmentPresenter extends BasePresenter {

    private SettingsFragmentView mView;

    public SettingsFragmentPresenter(SettingsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
