package com.volcengine.zeus.plugin2_impl;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.volcengine.zeus.plugin2_api.Plugin2;

/**
 * @author xuekai
 * @date 2021/6/3
 */
public class Plugin2Application extends Application {
    Context mContext;
    Activity mActivity;

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        Plugin2.api = new Plugin2Impl();
        //插件加载成功之后，注册插件activity和桩的对应关系，正常只需要在插件中注册，宿主注册的话，会覆盖插件的配置，
        // 可以实现宿主中修改桩的横竖屏之类的配置。插件的manifest的activity配置应该是不会生效的（主题应该要生效吧）
        String stubPrefix = "com.volcengine.zeus.plugin2_api";
        _registerZeusActivityStub(stubPrefix + ".stub.activity.Stub_Activity_Test",
                Plugin2MainActivity.class.getName());
        mContext = base;
        mActivity = null;

    }

    private static void _registerZeusActivityStub(String stubActivity, String... targetActivities) {
        throw new RuntimeException("stub");
    }
}
