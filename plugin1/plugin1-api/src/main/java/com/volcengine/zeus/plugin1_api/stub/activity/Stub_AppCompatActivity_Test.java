package com.volcengine.zeus.plugin1_api.stub.activity;

import com.volcengine.zeus.activity.GenerateProxyAppCompatActivity;
import com.volcengine.zeus.plugin1_api.Plugin1Constant;

/**
 * @author xuekai
 * @date 8/31/21
 */
public class Stub_AppCompatActivity_Test extends GenerateProxyAppCompatActivity {
    @Override
    public String getPluginPkgName() {
        return Plugin1Constant.pluginPkg;
    }
}
