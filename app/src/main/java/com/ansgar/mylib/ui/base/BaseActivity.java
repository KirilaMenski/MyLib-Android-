package com.ansgar.mylib.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

/**
 * Created by kirill on 24.1.17.
 */

public abstract class BaseActivity extends BaseRxActivity implements BaseContextView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bindView();
    }

    protected void bindView() {
        ButterKnife.bind(this);
    }

    protected abstract BasePresenter getPresenter();

    protected abstract void createPresenter();

    public void hideKeyBoard() {
        Activity activity = this;
        if (isFinishing(activity)) return;
        View view = activity.getCurrentFocus();
        if (view != null) {
            hideKeyBoard(activity, view);
        }
    }

    public void hideKeyBoard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public boolean isFinishing(Activity activity) {
        return activity == null || activity.isFinishing() || activity.isDestroyed();
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

}
