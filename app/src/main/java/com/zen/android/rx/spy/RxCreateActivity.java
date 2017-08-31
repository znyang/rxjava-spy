package com.zen.android.rx.spy;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import java.util.concurrent.TimeUnit;

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

        Observable<String> obs = onCreateObs();//Observable.create(new CreateSubscribe());

        Subscription subscription = obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

        Log.w("RxJava", "destroy "+toString());
    }

    Observable<String> onCreateObs() {
        return Observable.timer(30, TimeUnit.SECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                return aLong.toString();
            }
        });
//        return rx.Observable.defer(new Func0<rx.Observable<String>>() {
//            @Override
//            public Observable<String> call() {
//                SystemClock.sleep(100000);
//                return Observable.just("1", "2", "3");
//            }-
//        });
    }

    static class CreateSubscribe implements Observable.OnSubscribe<String> {

        @Override
        public void call(Subscriber<? super String> subscriber) {
            SystemClock.sleep(10000);
            subscriber.onNext("1");
            SystemClock.sleep(100000);
            subscriber.onNext("2");
            subscriber.onCompleted();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.w("RxJava", "finalize " + toString());
    }
}
