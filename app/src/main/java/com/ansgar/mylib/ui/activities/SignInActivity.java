package com.ansgar.mylib.ui.activities;

import android.content.Context;
import android.content.Intent;

import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.activity.SignInActivityPresenter;
import com.ansgar.mylib.ui.view.SignInActivityView;

/**
 * Created by kirill on 24.1.17.
 */

public class SignInActivity extends BaseActivity implements SignInActivityView {

    private SignInActivityPresenter mPresenter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        return intent;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SignInActivityPresenter(this);
    }
}
