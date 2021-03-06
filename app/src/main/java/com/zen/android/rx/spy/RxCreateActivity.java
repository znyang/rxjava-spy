package com.zen.android.rx.spy;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import rx.Observable;
import rx.Subscriber;

public class RxCreateActivity extends BaseRxCaseWithActionActivity<String> {

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
            SystemClock.sleep(5000);
            subscriber.onNext("1");
            SystemClock.sleep(20000);
            subscriber.onNext("2");
            Log.w("RxJava", "sleep end");
            subscriber.onCompleted();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.w("RxJava", "finalize " + toString());
    }
}
