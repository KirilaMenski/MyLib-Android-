package com.ansgar.mylib.ui.activities;

import android.os.Bundle;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.activity.SplashActivityPresenter;
import com.ansgar.mylib.ui.view.SplashActivityView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 24.1.17.
 */

public class SplashActivity extends BaseActivity implements SplashActivityView {

    private static final int LAYOUT = R.layout.activity_splash;

    SplashActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
        Observable<String> observable = Observable.just("0").delay(1500, TimeUnit.MILLISECONDS);
        createAndAddSubscription(observable, observer);
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
//            startActivity(MainActivity.newIntent(SplashActivity.this));
            startActivity(SignInActivity.newIntent(SplashActivity.this));
            finish();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();

        }

        @Override
        public void onNext(String s) {

        }
    };
}
