package com.ansgar.mylib.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.ProfileFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.ProfileFragmentView;

import butterknife.ButterKnife;

/**
 * Created by kirill on 24.1.17.
 */

public class ProfileFragment extends BaseFragment implements ProfileFragmentView {

    private static final int LAYOUT = R.layout.fragment_profile;

    private ProfileFragmentPresenter mPresenter;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        mPresenter = new ProfileFragmentPresenter(this);
    }
}
