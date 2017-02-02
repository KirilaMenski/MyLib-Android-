package com.ansgar.mylib.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.SettingsFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.SettingsFragmentView;

import butterknife.ButterKnife;

/**
 * Created by kirill on 2.2.17.
 */

public class SettingsFragment extends BaseFragment implements SettingsFragmentView {

    private static final int LAYOUT = R.layout.fragment_settings;
    private SettingsFragmentPresenter mPresenter;

    public static SettingsFragment newInstance(){
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SettingsFragmentPresenter(this);
    }
}
