package com.volcengine.zeus

import android.app.Application
import com.volcengine.zeus.plugin1_api.Plugin1
import com.volcengine.zeus.plugin2_api.Plugin2

class ZeusDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalParam.getInstance().isDebug = true
        GlobalParam.getInstance().isFastDex2oat = false
//        Fresco.initialize(this);
        Plugin1.init(this)
        Plugin2.init(this)
    }
}