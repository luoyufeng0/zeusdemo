package com.volcengine.zeus.plugin1_impl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * @author xuekai
 * @date 2021/6/10
 */
public class LivePluginService extends Service {
    private static final String TAG = "LivePluginService";

    public LivePluginService() {
        Log.e(TAG, "LivePluginService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind" + Thread.currentThread());
        Log.d(TAG, "thread:" + Thread.currentThread());
        return new LivePluginBinder();
    }

    static class LivePluginBinder extends Binder {
        long createTime = System.currentTimeMillis();

        public void play() {
            Log.d(TAG, "通过binder调用play方法. binder创建时间：" + createTime);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand" + getClassLoader() + " " + getResources());
        Log.d(TAG, "thread:" + Thread.currentThread());
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(10000);
//                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy" + getClassLoader() + " " + getResources());
        Log.d(TAG, "thread:" + Thread.currentThread());
    }
}
