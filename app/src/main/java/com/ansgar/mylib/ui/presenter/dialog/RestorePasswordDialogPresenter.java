package com.ansgar.mylib.ui.presenter.dialog;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.dialog.RestorePasswordDialogView;

/**
 * Created by kirill on 24.1.17.
 */
public class RestorePasswordDialogPresenter extends BasePresenter {

    RestorePasswordDialogView mView;

    public RestorePasswordDialogPresenter(RestorePasswordDialogView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
