package com.volcengine.zeus.plugin1_impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.volcengine.zeus.SameNameView;
import com.volcengine.zeus.Zeus;
import com.volcengine.zeus.plugin1_api.Plugin1Constant;

/**
 * @author xuekai
 * @date 2021/6/1
 */
public class Plugin1MainActivity extends AppCompatActivity {
    SameNameView sameNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin1_main);
//        ((TextView) findViewById(R.id.textView)).setText("插件1 版本号：" + Zeus.getPlugin(Plugin1Constant.pluginPkg).getVersion() + getString(R.string.plugin1_api_hi_java_code));
//        sameNameView = findViewById(R.id.sameName);
//        System.out.println("cls:"+sameNameView.getClass()+" cl:"+sameNameView.getClass().getClassLoader());
//
//
//        View inflate = getLayoutInflater().inflate(R.layout.activity_plugin1_main, null);
//        System.out.println("cls1:"+inflate.findViewById(R.id.sameName).getClass()+"  "+inflate.findViewById(R.id.sameName).getClass().getClassLoader());
    }
}
