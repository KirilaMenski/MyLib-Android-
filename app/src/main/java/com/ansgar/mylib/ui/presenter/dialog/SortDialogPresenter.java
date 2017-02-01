package com.ansgar.mylib.ui.presenter.dialog;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.dialog.SortDialogView;

/**
 * Created by kirill on 1.2.17.
 */
public class SortDialogPresenter extends BasePresenter {

    private SortDialogView mView;

    public SortDialogPresenter(SortDialogView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
