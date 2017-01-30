package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorBooksPagerView;

/**
 * Created by kirill on 30.1.17.
 */
public class AuthorBooksPagerPresenter extends BasePresenter {

    private AuthorBooksPagerView mView;

    public AuthorBooksPagerPresenter(AuthorBooksPagerView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
