package com.volcengine.zeus.plugin2_impl;

import android.app.Activity;
import android.content.Context;

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

    public static void setContentView(Activity context) {
        context.setContentView(R.layout.activity_plugin22);
        context.setContentView(R.layout.activity_plugin22);

        new Thread(() -> System.out.println("xx")).start();
    }
}
