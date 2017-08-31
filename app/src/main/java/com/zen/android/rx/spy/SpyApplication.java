package com.zen.android.rx.spy;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.internal.schedulers.CachedThreadScheduler;
import rx.plugins.RxJavaObservableExecutionHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;

import java.util.concurrent.ThreadFactory;

/**
 * @author zenyang
 *         created on 2017/8/30
 */
public class SpyApplication extends Application{

    static {
        System.setProperty("rx.io-scheduler.keepalive", "5");
    }

    Scheduler ioScheduler = new CachedThreadScheduler(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            return new SpyThread(runnable);
        }
    });

    static class SpyThread extends Thread{
        public SpyThread(Runnable target) {
            super(target);
        }

        @Override
        public void run() {
            Log.w("RxJava", "start thread "+Thread.currentThread().getName());
            super.run();
            Log.w("RxJava", "end thread "+Thread.currentThread().getName());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook(){
            @Override
            public Scheduler getIOScheduler() {
                return ioScheduler;
            }
        });
    }
}
