package com.volcengine.zeus.plugin2_impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Create by WUzejian on 2021/6/18.
 * demo test
 */
public class PanglePluginReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("wzj","PanglePluginReceiver....invoke");
        Toast.makeText(context, "PanglePluginReceiver....invoke", Toast.LENGTH_SHORT).show();
    }
}
