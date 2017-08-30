package com.zen.android.rx.spy;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import rx.CompletableSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RxCreateActivity extends AppCompatActivity {

    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Observable<String> obs = Observable.create(new CreateSubscribe());

        Subscription subscription = obs.subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("RxJava", s);
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    static class CreateSubscribe implements Observable.OnSubscribe<String>{

        @Override
        public void call(Subscriber<? super String> subscriber) {
            SystemClock.sleep(10000);
            subscriber.onNext("1");
            SystemClock.sleep(100000);
            subscriber.onNext("2");
            subscriber.onCompleted();
        }
    }

}
