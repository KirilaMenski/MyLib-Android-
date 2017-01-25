package com.ansgar.mylib.ui.presenter.activity;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.activity.MainActivityView;

/**
 * Created by kirill on 25.1.17.
 */
public class MainActivityPresenter extends BasePresenter {

    private MainActivityView mView;

    public MainActivityPresenter(MainActivityView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
