package com.volcengine.zeus.plugin1_impl;

import android.content.Context;
import android.content.Intent;

import com.volcengine.zeus.plugin1_api.IPlugin1Api;

/**
 * @author xuekai
 * @date 8/31/21
 */
class Plugin1Impl implements IPlugin1Api {
    @Override
    public void startActivity(Context context, String tag) {
        context.startActivity(new Intent(context, Plugin1MainActivity.class));
    }
}
