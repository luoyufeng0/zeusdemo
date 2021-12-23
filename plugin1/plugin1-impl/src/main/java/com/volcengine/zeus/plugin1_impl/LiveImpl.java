package com.volcengine.zeus.plugin1_impl;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Keep;


/**
 * @author xuekai
 * @date 6/28/21
 */
public class LiveImpl {
    public static Context c;

    public static void test(Context context) {
        c = context;
        System.out.println(c.getResources());
    }

    @Keep
    public static void setContentView(Activity context) {
        context.setContentView(R.layout.activity_plugin1);
        context.setContentView(R.layout.activity_plugin1);

        new Thread(() -> System.out.println("xx")).start();
    }
}
