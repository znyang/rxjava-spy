package com.zen.android.rx.spy.not;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.zen.android.rx.spy.R;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: zen. date: 2017/11/30 0030
 */
public class NumberOfThreadActivity extends AppCompatActivity {

    TextView mTvCount;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_not);
        mTvCount = (TextView) findViewById(R.id.tv_count);
    }

    public void onStart(View view) {
        start();
    }

    private void start() {
        mTvCount.setText(String.valueOf(NotThread.count));
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new NotThread().start();
                new NotThread().start();
                new NotThread().start();
                start();
            }
        }, 16);
    }

    static class NotThread extends Thread {

        static final AtomicLong sIndex = new AtomicLong();
        static       long       count  = 1;

        @Override
        public void run() {
            count = sIndex.addAndGet(1);
            super.run();
        }
    }
}
