package com.volcengine.zeus.plugin1_impl;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author xuekai
 * @date 2021/6/10
 */
public class LivePluginIntentService extends IntentService {
    private static final String TAG = "LivePluginService";

    public LivePluginIntentService() {
        super("livep");
        Log.e(TAG, "LivePluginService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return new LivePluginBinder();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "onHandleIntent" + Thread.currentThread());

    }

    static class LivePluginBinder extends Binder {
        long createTime = System.currentTimeMillis();

        public void play() {
            Log.d(TAG, "通过binder调用play方法. binder创建时间：" + createTime);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand" + getClassLoader() + " " + getResources());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy" + getClassLoader() + " " + getResources());
    }
}
