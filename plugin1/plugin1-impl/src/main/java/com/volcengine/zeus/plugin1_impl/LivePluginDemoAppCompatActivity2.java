package com.volcengine.zeus.plugin1_impl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;



/**
 * @author xuekai
 * @date 2021/6/1
 */
public class LivePluginDemoAppCompatActivity2 extends AppCompatActivity {
    private static final String TAG = "LivePluginDemoAppCompat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin2);
        Intent intent = new Intent();
        setResult(-99);
    }

//    public Application getApplication() {
//        return null;
//    }


    public void startLivePluginService(View v) {
        startService(new Intent(this, LivePluginService.class));
    }


    public void stopLivePluginService(View view) {
        stopService(new Intent(this, LivePluginService.class));
    }

    private LivePluginService.LivePluginBinder service1;

    ServiceConnection conn = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected   " + name);
            service1 = (LivePluginService.LivePluginBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected   " + name);
            service1 = null;
        }
    };

    public void bindLivePluginService(View view) {
        bindService(new Intent(this, LivePluginService.class), conn, 0);
    }

    public void unbindLivePluginService(View view) {
        unbindService(conn);
    }

    public void play(View view) {
        if (service1 != null) {
            service1.play();
        }
    }
}
