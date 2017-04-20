package com.ansgar.mylib.ui.activities;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.activity.SplashActivityPresenter;
import com.ansgar.mylib.ui.view.activity.SplashActivityView;
import com.ansgar.mylib.util.LanguageUtil;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.concurrent.TimeUnit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 24.1.17.
 */

public class SplashActivity extends BaseActivity implements SplashActivityView {

    private final int PERMISSION_REQUEST_CODE = 1;

    private SplashActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        createPermissions();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SplashActivityPresenter(this);

    }

    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {

            if (MyLibPreference.getUserId() == -1) {
                startActivity(LogInActivity.newIntent(SplashActivity.this));
            } else {
                startActivity(MainActivity.newIntent(SplashActivity.this));
            }
            finish();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();

        }

        @Override
        public void onNext(String s) {
            LanguageUtil.setCurrentLang(getContext());
        }
    };

    private void createPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyPermission()) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private boolean checkIfAlreadyPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mPermissionsEnabled = true;
//                } else {
//
//                }
                Observable<String> observable = Observable.just("0").delay(1500, TimeUnit.MILLISECONDS);
                createAndAddSubscription(observable, observer);
                return;
        }
    }
}
