package com.volcengine.zeus.plugin2_impl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.volcengine.zeus.Zeus;
import com.volcengine.zeus.plugin2_api.Plugin2Constant;

/**
 * @author xuekai
 * @date 2021/6/1
 */
public class Plugin2MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin2_main);
        ((TextView) findViewById(R.id.textView)).setText("插件2 版本号：" + Zeus.getPlugin(Plugin2Constant.pluginPkg).getVersion());
    }

}
