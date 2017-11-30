package com.zen.android.rx.spy;

import android.util.Log;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author: zen
 * date: 2017/9/18 0018.
 */
public abstract class BaseRxCaseWithActionActivity<Result> extends BaseRxCaseActivity<Result> {

    protected Subscription onCreateSubscription(Observable<Result> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxAction<Result>());
    }

    static class RxAction<R> implements Action1<R> {

        @Override
        public void call(R result) {
            Log.d(TAG_RX, result.toString());
        }
    }

}
