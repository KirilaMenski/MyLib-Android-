package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.ProfileFragmentView;

/**
 * Created by kirill on 24.1.17.
 */
public class ProfileFragmentPresenter extends BasePresenter {

    private ProfileFragmentView mView;

    public ProfileFragmentPresenter(ProfileFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
