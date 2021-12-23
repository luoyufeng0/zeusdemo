package com.volcengine.zeus.plugin2_impl;

import android.content.Context;
import android.content.Intent;

import com.volcengine.zeus.plugin2_api.IPlugin2Api;

/**
 * @author xuekai
 * @date 8/31/21
 */
class Plugin2Impl implements IPlugin2Api {
    @Override
    public void startActivity(Context context, String tag) {
        context.startActivity(new Intent(context, Plugin2MainActivity.class));
    }
}
