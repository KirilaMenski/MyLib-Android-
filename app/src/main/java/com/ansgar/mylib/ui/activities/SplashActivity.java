package com.ansgar.mylib.ui.activities;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.activity.SplashActivityPresenter;
import com.ansgar.mylib.ui.view.activity.SplashActivityView;
import com.ansgar.mylib.util.LanguageUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.ansgar.mylib.util.PermissionsUtil;

import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.support.annotation.NonNull;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 24.1.17.
 */

public class SplashActivity extends BaseActivity implements SplashActivityView {

    private SplashActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        boolean isMVersion = new PermissionsUtil(this).requestPermission(new String[]{PermissionsUtil.WRITE_EXTERNAL_STORAGE,
                        PermissionsUtil.READ_EXTERNAL_STORAGE,
                        PermissionsUtil.INTERNET, PermissionsUtil.ACCESS_NETWORK_STATE},
                PermissionsUtil.MAIN_PERMISSION_REQUEST_CODE);
        if (!isMVersion) startObservable();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionsUtil.MAIN_PERMISSION_REQUEST_CODE) {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mPermissionsEnabled = true;
//                } else {
//
//                }
            startObservable();
        }
    }

    private void startObservable() {
        Observable<String> observable = Observable.just("0").delay(500, TimeUnit.MILLISECONDS);
        createAndAddSubscription(observable, observer);
    }

}
