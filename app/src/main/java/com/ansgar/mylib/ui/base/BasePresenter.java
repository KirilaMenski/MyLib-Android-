package com.ansgar.mylib.ui.base;

import android.content.Context;
import android.os.Bundle;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kirill on 24.1.17.
 */

public abstract class BasePresenter {

    private static final String TAG = BasePresenter.class.getSimpleName();
    private final Context context;
    CompositeSubscription compositeSubscription;

    public BasePresenter(Context context) {
        this.context = context.getApplicationContext();
    }

    public void bindObservable(Observable<?> observable, Observer sub) {
        if(compositeSubscription == null) compositeSubscription = new CompositeSubscription();
        addSubscription(observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub));
    }

    public void bindObservableWithoutComposite(Observable<?> observable, Observer sub) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }

    public void bindObservable(Observable<?> observable) {
        addSubscription(observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe());
    }

    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public void onStart(Context context) {
        compositeSubscription = new CompositeSubscription();
    }

    public void onStop(Context context) {
        if (compositeSubscription == null) {
            return;
        }
        if (!compositeSubscription.isUnsubscribed() && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
            compositeSubscription.clear();
        }
    }

    public void saveState(Bundle outState) {
    }

    public void restoreState(Bundle savedState) {

    }

    public abstract BaseContextView getView();

    public Context getContext() {
        return getView().getContext();
    }

}
