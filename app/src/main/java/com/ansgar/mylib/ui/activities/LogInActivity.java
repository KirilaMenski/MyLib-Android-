package com.ansgar.mylib.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.RegistrationDialog;
import com.ansgar.mylib.ui.presenter.activity.LogInActivityPresenter;
import com.ansgar.mylib.ui.presenter.dialog.RestorePasswordDialog;
import com.ansgar.mylib.ui.view.activity.LogInActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 24.1.17.
 */

public class LogInActivity extends BaseActivity implements LogInActivityView {

    private static final int LAYOUT = R.layout.activity_login;

    private LogInActivityPresenter mPresenter;

    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login)
    TextView mLogin;
    @BindView(R.id.restore_pass)
    TextView mRestorePass;
    @BindView(R.id.registration)
    TextView mRegistration;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LogInActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @OnClick(R.id.login)
    public void loginPressed() {
        mPresenter.login(mEmail.getText().toString().trim(), mPassword.getText().toString().trim());
    }

    @OnClick(R.id.restore_pass)
    public void restorePassPressed() {
        RestorePasswordDialog dialog = RestorePasswordDialog.newInstance();
        dialog.show(getSupportFragmentManager(), getResources().getString(R.string.restore_pass));
    }

    @OnClick(R.id.registration)
    public void registrationPressed() {
        RegistrationDialog dialog = RegistrationDialog.newInstance();
        dialog.show(getSupportFragmentManager(), "registrationDialog");
    }

    @Override
    protected void createPresenter() {
        mPresenter = new LogInActivityPresenter(this);
    }
}
