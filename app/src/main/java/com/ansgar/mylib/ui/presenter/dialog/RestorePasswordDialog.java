package com.ansgar.mylib.ui.presenter.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.dialog.RestorePasswordDialogView;

import butterknife.ButterKnife;

/**
 * Created by kirill on 24.1.17.
 */

public class RestorePasswordDialog extends BaseDialog implements RestorePasswordDialogView {

    private static final int LAYOUT = R.layout.dialog_restore_pass;

    private RestorePasswordDialogPresenter mPresenter;
    private Dialog mDialog;

    public static RestorePasswordDialog newInstance() {
        RestorePasswordDialog dialog = new RestorePasswordDialog();
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(LAYOUT, null);
        ButterKnife.bind(this, view);
        mDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDialog.dismiss();
                }
                return true;
            }
        });
        return mDialog;
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new RestorePasswordDialogPresenter(this);
    }
}
