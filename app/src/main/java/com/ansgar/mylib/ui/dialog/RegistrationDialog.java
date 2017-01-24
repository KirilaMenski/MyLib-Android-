package com.ansgar.mylib.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.listener.RegistrationDialogListener;
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.dialog.RegistrationDialogPresenter;
import com.ansgar.mylib.ui.view.dialog.RegistrationDialogView;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by kirill on 24.1.17.
 */

public class RegistrationDialog extends BaseDialog implements RegistrationDialogView {

    private static final int LAYOUT = R.layout.dialog_registration;

    RegistrationDialogPresenter mPresenter;

    Dialog mDialog;
    WeakReference<RegistrationDialogListener> mListener;

    EditText mEmail;
    EditText mPassword;
    TextView mRegistration;

    public static final RegistrationDialog newInstance() {
        RegistrationDialog dialog = new RegistrationDialog();
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

    //    @OnClick()
    public void sendData() {
        mPresenter.sendForm(mEmail.getText().toString().trim(), mPassword.getText().toString().trim());
    }

    public void setListener(RegistrationDialogListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void createPresenter() {

    }
}
