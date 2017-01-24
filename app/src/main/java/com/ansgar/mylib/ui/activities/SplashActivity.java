package com.ansgar.mylib.ui.activities;

import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.activity.SplashActivityPresenter;
import com.ansgar.mylib.ui.view.SplashActivityView;

/**
 * Created by kirill on 24.1.17.
 */

public class SplashActivity extends BaseActivity implements SplashActivityView {


    SplashActivityPresenter mPresenter;

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SplashActivityPresenter(this);
    }
}
