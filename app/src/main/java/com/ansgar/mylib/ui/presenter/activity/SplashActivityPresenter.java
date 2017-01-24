package com.ansgar.mylib.ui.presenter.activity;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.SplashActivityView;

/**
 * Created by kirill on 24.1.17.
 */
public class SplashActivityPresenter extends BasePresenter {

    SplashActivityView mView;

    public SplashActivityPresenter(SplashActivityView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
