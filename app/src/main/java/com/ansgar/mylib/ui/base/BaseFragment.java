package com.ansgar.mylib.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ansgar.mylib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kirill on 24.1.17.
 */

public abstract class BaseFragment extends Fragment implements BaseFragmentView {

    public boolean mKeyboardState = false;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void hideKeyBoard() {
        Activity activity = getActivity();
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            mKeyboardState = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart(getActivity());
    }

    @Override
    public void onStop() {
        getPresenter().onStop(null);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getPresenter().saveState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        getPresenter().restoreState(savedInstanceState);
    }

    public abstract BasePresenter getPresenter();

    protected abstract void createPresenter();

    @Override
    public Context getContext() {
        return getActivity();
    }

}
