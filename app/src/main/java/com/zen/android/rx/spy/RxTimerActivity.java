package com.zen.android.rx.spy;

import android.os.Bundle;
import rx.Observable;
import rx.functions.Func1;

import java.util.concurrent.TimeUnit;

public class RxTimerActivity extends BaseRxCaseActivity<String> {

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_timer);
    }

    @Override
    Observable<String> onCreateObservable() {
        return Observable.timer(30, TimeUnit.SECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong.toString();
                    }
                });
    }
}
