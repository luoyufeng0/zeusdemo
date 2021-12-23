package com.volcengine.zeus.plugin1_api;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.volcengine.zeus.Zeus;

/**
 * @author xuekai
 * @date 8/31/21
 */
public class Plugin1 {
    public static IPlugin1Api api = null;

    public static void init(Context context) {

        Zeus.init((Application) context.getApplicationContext());
        Zeus.installFromDownloadDir();
        Zeus.fetchPlugin(Plugin1Constant.pluginPkg);
    }

    public static void loadPlugin(Context context) {
        if (!Zeus.isPluginInstalled(Plugin1Constant.pluginPkg)) {
            Toast.makeText(context, "插件1未安装", Toast.LENGTH_SHORT).show();
        } else {
            if (Zeus.isPluginLoaded(Plugin1Constant.pluginPkg)) {
                // ok
//                Toast.makeText(context, "插件1加载成功", Toast.LENGTH_SHORT).show();
            } else {
                boolean loadPlugin = Zeus.loadPlugin(Plugin1Constant.pluginPkg);
                if (loadPlugin) {
                    Toast.makeText(context, "插件1加载成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "插件1加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static void jump2Plugin1Activity(Context context) {
        try {
            api.startActivity(context, "LivePluginDemoAppCompatActivity");
        } catch (Throwable t) {
            t.printStackTrace();
            Toast.makeText(context, "启动失败", Toast.LENGTH_SHORT).show();
        }
    }
}
