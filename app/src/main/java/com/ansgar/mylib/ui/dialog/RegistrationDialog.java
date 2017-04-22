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
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.dialog.RegistrationDialogPresenter;
import com.ansgar.mylib.ui.view.dialog.RegistrationDialogView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 24.1.17.
 */

public class RegistrationDialog extends BaseDialog implements RegistrationDialogView {

    private RegistrationDialogPresenter mPresenter;

    private Dialog mDialog;

    @BindView(R.id.first_name)
    EditText mFirstName;
    @BindView(R.id.last_name)
    EditText mLastName;
    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.registration)
    TextView mRegistration;

    public static final RegistrationDialog newInstance() {
        RegistrationDialog dialog = new RegistrationDialog();
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_registration, null);
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
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    @OnClick(R.id.registration)
    public void sendData() {
        mPresenter.sendForm(mFirstName.getText().toString(), mLastName.getText().toString(),
                mEmail.getText().toString().trim(), mPassword.getText().toString().trim(), mConfirmPassword.getText().toString());
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new RegistrationDialogPresenter(this);
    }

    @Override
    public void setPass(String text) {
        mPassword.setText(text);
    }

    @Override
    public void setConfirmPass(String text) {
        mConfirmPassword.setText(text);
    }
}
