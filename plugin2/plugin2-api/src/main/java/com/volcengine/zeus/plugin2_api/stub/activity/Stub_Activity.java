package com.volcengine.zeus.plugin2_api.stub.activity;

import com.volcengine.zeus.activity.GenerateProxyActivity;
import com.volcengine.zeus.plugin2_api.Plugin2Constant;

/**
 * @author xuekai
 * @date 8/31/21
 */
public class Stub_Activity extends GenerateProxyActivity {
    @Override
    public String getPluginPkgName() {
        return Plugin2Constant.pluginPkg;
    }
}
