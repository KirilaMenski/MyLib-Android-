package com.ansgar.mylib.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kirill on 24.1.17.
 */

public class BaseRxActivity extends AppCompatActivity {

    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        subscribe();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribeAll();
    }

    private void subscribe() {
        for (Subscription subscription : createSubscriptions()) {
            mSubscriptions.add(subscription);
        }
    }

    protected void unsubscribeAll() {
        if (mSubscriptions == null) {
            return;
        }
        mSubscriptions.clear();
        mSubscriptions = new CompositeSubscription();
    }

    protected void unsubscribe(Subscription subscription) {
        if (subscription == null || mSubscriptions == null) {
            return;
        }
        mSubscriptions.remove(subscription);
    }

    public Subscription addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
        return subscription;
    }

    protected List<Subscription> createSubscriptions() {
        return new ArrayList<>(0);
    }

    // quick subscriptions

    protected <T> Subscription createAndAddSubscription(Observable<T> observable, Observer<T> observer) {
        return addSubscription(bindObservable(observable, observer));
    }

    protected <T> Subscription bindObservable(Observable<T> observable, Observer<T> observer) {
        return observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
