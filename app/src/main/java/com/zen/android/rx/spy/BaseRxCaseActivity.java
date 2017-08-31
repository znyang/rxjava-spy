package com.zen.android.rx.spy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author: zen
 * date: 2017/8/31 0031.
 */
public abstract class BaseRxCaseActivity<Result> extends AppCompatActivity {

    static final String TAG_RX = "RxJava";

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    abstract void onBaseCreate(@Nullable Bundle saveInstanceState);

    abstract Observable<Result> onCreateObservable();

    protected Subscription onCreateSubscription(Observable<Result> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result>() {
                    @Override
                    public void call(Result s) {
                        Log.d(TAG_RX, s.toString());
                    }
                });
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBaseCreate(savedInstanceState);

        Observable<Result> obs = onCreateObservable();
        Subscription subscription = onCreateSubscription(obs);
        Log.w(TAG_RX, "subscribe " + subscription.toString());

        mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();

        Log.w(TAG_RX, "destroy " + toString());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.w(TAG_RX, "finalize " + toString());
    }
}
