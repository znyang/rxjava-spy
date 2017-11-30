package com.zen.android.rx.spy;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

public class RxDeferActivity extends BaseRxCaseActivity<String> {

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_create);
    }

    @Override
    Observable<String> onCreateObservable() {
        return Observable.defer(new DeferObservable());
    }

    static class DeferObservable implements Func0<Observable<String>> {

        @Override
        public Observable<String> call() {
            SystemClock.sleep(10000);
            Log.w("RxJava", "sleep end");
            return Observable.just("1");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.w("RxJava", "finalize " + toString());
    }
}
