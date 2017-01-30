package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.BookCitationsPagerView;

/**
 * Created by kirill on 30.1.17.
 */
public class BookCitationsPagerPresenter extends BasePresenter {

    private BookCitationsPagerView mView;

    public BookCitationsPagerPresenter(BookCitationsPagerView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
