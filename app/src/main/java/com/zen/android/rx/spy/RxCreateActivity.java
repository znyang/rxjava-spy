package com.zen.android.rx.spy;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import rx.Observable;
import rx.Subscriber;

public class RxCreateActivity extends BaseRxCaseActivity<String> {

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_create);
    }

    @Override
    Observable<String> onCreateObservable() {
        return Observable.create(new CreateSubscribe());
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
