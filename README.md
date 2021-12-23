# [Zeusdemo Github下载](https://github.com/sxiaofeng/zeusdemo)

# Zeusdemo工程结构介绍

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=NzI3ZWRkODlmMWY3NDRkZGMxZTUwMzNkNjM3NGI2MjlfNUpQMUJSeERFeXBLc25RSmtTNjYyUUtNZXVQWmduUDdfVG9rZW46Ym94Y25iZURPUzJWRHN5bmloN0dqSkhJOE9oXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)





Zeusdemo工程分为以下几个module

- app module ：宿主模块，依赖了zeus-core、插件的api层。真实使用场景中，宿主不需要和其他模块在一起，只需要依赖相应的插件api层即可。
- zeus-fake-host：一个假的宿主模块，主要用于配合插件打包。
- sdkPlugin：插件module，主要用于输出一个插件Apk，此module下的build/outputs/apk下的apk文件是真正的插件apk。（如项目中的plugin1/plugin1和plugin2/plugin2）
- sdkApi：插件的api层，业务SDK提供的一个aar，由宿主App依赖，会打入宿主，向宿主提供插件的能力。（如项目中的plugin1/plugin1-api和plugin2/plugin2-api）
- sdkImpl：业务SDK提供的一个aar，由sdkPlugin依赖，真正插件的代码，包含了需要动态下发的所有代码。（如项目中的plugin1/plugin1-impl和plugin2/plugin2-impl）



# 如何动态运行插件

sdk插件是独立于宿主的一部分，可以通过push、内置、平台下发的三种方式动态加载运行插件

## 一、用adb push的方式让插件跑起来

### 1、更改plugin1页面内容

将plugin1/plugin1impl/src/main/res/layout/activity_plugin1_main.xml页面的textview的text值改为"**这是****adb** **push的插件**"

### 2、编译打包plugin1

执行下面命令,打包编译生成新的插件apk，并且会自动执行adb push命令，将插件

`./plugin1/plugin1/build/outputs/apk/debug/plugin1-debug.apk` push到

`/data/data/com.volcengine.zeusdemo/files/.zeus_d/plugin.apk`目录

```Java
./gradlew :plugin1:plugin1:assembleDebug
```

### 3、启动宿主

启动宿主（**app module，不是zeus-fake-host**），点击`启动PLUGIN1插件ACTIVITY`按钮，显示如下。此时显示的就是刚刚push的插件

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=MmQ2YTAwNWRhNzQyZmZjZjk0ZWUwYWM3MjJhY2ZhNDZfVEl5ek9UQnF3cVByck54UkxXMXhyY2JiNmwyT3I3SG9fVG9rZW46Ym94Y25OWEhRM0VwekpaUmxXeG1td2pjbUNjXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)





## 二、内置插件运行

### 1、更改plugin1页面内容

将plugin1/plugin1impl/src/main/res/layout/activity_plugin1_main.xml页面的textview的text值改为"**这是内置的插件**"

### 2、插件内置到宿主中

将plugin1/plugin1/build.gradle文件中的**afterEvaluate闭包**中的内容注释掉（防止编译后直接将插件push进宿主）

执行下方命令，重新编译打包plugin1

```Java
./gradlew :plugin1:plugin1: assembleDebug 
```

将编译好的插件plugin1/plugin1/build/outputs/apk/debug/plugin1-debug.apk，复制进plugin1/plugin1-api/src/main/assets/zeus_p/ 目录



### 3、修改plugin1-api模块的AndroidManifest.xml文件

将meta-data模块的internalPath属性改为相应的路径，并设置版本号

```Groovy
internalPath:'zeus_p/plugin1-debug.apk',

internalVersionCode:5,
```

### 4、运行宿主模块（app模块）

点击`启动PLUGIN1插件ACTIVITY`，跳转到插件，显示如下,内置插件运行成功

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=Yzc4OGMzMTFiYTNlMjI5ZGNiMDViYTM3NDA1ZmYwMmVfUmwybUpTUkJUb3p1TDdub01QUkVsWVNObVliaXg1cXBfVG9rZW46Ym94Y25Oa2VUYUZ0WGJKeVhIYzhTajhGN1BiXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)





## 三、火山引擎平台下发的方式运行插件

### 1、修改plugin1代码

将plugin1/plugin1impl/src/main/res/layout/activity_plugin1_main.xml页面的textview的text值改为"**这是平台下发插件"**

### 2、修改plugin1模块的包名

因为火山引擎不允许重复的包名，所以必须先修改插件的包名再将插件上传至火山引擎

将以下四个文件中的com.volcengine.zeus.plugin1改为自己定义的包名

- plugin1/plugin1/src/main/AndroidManifest.xml
- plugin1/plugin1-api/src/main/java/com/volcengine/zeus/plugin1_api/Plugin1Constant.java
- plugin1/plugin1/build.gradle
- plugin1/plugin1-api/src/main/AndroidManifest.xml



### 3、修改plugin1插件版本信息

修改plugin1/plugin1/build.gradle文件**defaultConfig**闭包中的versionCode和versionName，修改为更高的版本，并重新打包编译。（一定要修改成比当前已安装插件版本更高的版本，不然不会下发）

```Nginx
defaultConfig {

    applicationId "com.volcengine.zeus.plugin1"

    // 不支持4.x，最小版本设置为21

    minSdkVersion 21

    targetSdkVersion 30

    versionCode 7  //修改为版本7

    versionName "7.0"



    testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

}
```

重写打包编译

```Java
./gradlew :plugin1:plugin1: assembleDebug  
```



### 4、根据[SDK热更新-火山引擎](https://www.volcengine.com/docs/6472/75320)文档创建sdk

- 创建sdk时，输入的包名为自己新定义的包名（如果提示应用包名已存在，说明有其他人创建过此包名的sdk，需要重新修改包名，重新编译打包）
- 新增sdk版本时

将plugin1/plugin1/build/outputs/apk/debug/plugin1-debug.apk插件拖入下方区域

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=OTFhZTIwZjI1YWE0NGM5Zjc1MDQzZjI5ODY1NzZmMzdfZmRYTHh3M1ZlYVRaZzc4UWM2YlZ1NHoyNXo5czRaN2NfVG9rZW46Ym94Y253ZTZDeTlvZFBjVHFhcmhuM3VzdkVjXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)

- 将状态信息改为已生效

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=MWZiN2M5MzZjNTQ3Njg4MTZjYzFlNWEyOTA5NGNlMjJfOXY2ZG9udjc4cXJjMnZHTHA2dEZWdjZtSjRWdnhKNzJfVG9rZW46Ym94Y24ydk12ODFKSTMweUhLT2YzSVpRY3RnXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)



### 5、修改Plugin1-api层的AndroidManifest文件

将meta-data块中的appkey和appSecretKey改为对应火山引擎插件的sdkkey和sdksecret

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=MDJlM2M0NjllYzExM2JjNmViZGYxMWRkMjZiZmZlZmRfWkhUUEk5MVQwdzFMZjdQVGFEeHU0UTFUNVppMW5PR29fVG9rZW46Ym94Y25Ud3Z4eFV6Z1VheVlha2tDM1BwblFCXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)



运行宿主，点击`启动PLUGIN1插件ACTIVITY`按钮，跳转到Plugin1的activity，显示是下发插件，下发插件成功

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjcxNDk3ODY4NzYwZjEyOTIwYjI1MTQxNjZkMTNhNzVfNDVEcWlGbkpYZnZKcnpCZmtNWlBWVkpKTkxZSUxjWWZfVG9rZW46Ym94Y25KTGRJZmQxM21Uc2ZOclVxSmtWNWljXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)





# 插件升级操作

当前下发的plugin1版本为7，我们让插件更新为版本8

### 1、修改plugin1代码

将plugin1/plugin1impl/src/main/res/layout/activity_plugin1_main.xml页面的textview的text值改为"**这是最新版本，8版本插件**"

### 2、修改plugin1版本信息

修改plugin1/plugin1/build.gradle文件**defaultConfig**闭包中的versionCode和versionName，修改为更高的版本，并重新打包编译。

```Nginx
defaultConfig {

    applicationId "com.volcengine.zeus.plugin1"

    // 不支持4.x，最小版本设置为21

    minSdkVersion 21

    targetSdkVersion 30

    versionCode 8  //修改为版本8

    versionName "8.0"



    testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

}
```



### 3、上传新版本插件

- 点击移动研发平台左侧sdk版本列表，选择当前插件，点击新增版本。

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=OTgyNjM5YzQ3ZTJhZmQ2NjE4YWUzMDgzZmU1YmVjODlfT1cyVUJjMXNUZmlKb0U5WGlLTWc2elhmTVg1T1V4VHRfVG9rZW46Ym94Y24yaFpQdUdodnlSaXJKZEt4VzA0UkhnXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)

- 将新版插件传入，并将状态信息改为**已生效**，
- 重新启动宿主，点击`启动PLUGIN1插件ACTIVITY`按钮，显示如下

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=MmM0ZWUyYzkyYjI0OTVkZDhiMTZjZTQ1Y2M2ZmMzZWZfV2x0dzVUeHZ4dXNDM3doS2tnY3pjNUF3YndLNlRaTlBfVG9rZW46Ym94Y25CcVBySmpLVkZNaFRDWGpZYnlzdjl5XzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)

# 插件回滚操作

我们将插件版本从8回滚到7

- 修改版本7的插件状态为**回滚中**
- 重新启动宿主，显示版本号为7，回滚成功

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=N2MxMTI5MWJlMzQ1ODYzYTQwMjY3ZGYyZTUzYzJlZmVfSzFRV1JsaWo0YWJoaEtydDRxT0Rtb09LZUxiQlF0ekNfVG9rZW46Ym94Y25aZ3NVSEpabjFGemhkblNuM1NKaHRkXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)





# 插件下线操作

- 修改当前插件版本状态为**卸载**（宿主中此版本的插件会被删除，但如果版本列表中还有其他已生效或回滚中的插件，则还会安装其他插件）
- 重新启动宿主app，plugin1显示为未安装（或者插件的版本号改变），当前版本插件下线操作成功



![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=Yzk5YTE3NmVkN2ZmOGFiNjNlNzVjOGFhNjhlZjVmMDZfMlQ5TndmQkdDMDVoV3pMMmlEU3ViZVVhdUpzbnR0T0FfVG9rZW46Ym94Y25NYlozUlljRjRzd09CN0ZhZGhWNEtiXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)





# 注意

- 每次修改插件的状态时，都要过10s才能生效



# 平台下发插件失败的可能原因

打开logcat，用**zeus/download**筛选日志



如果报下面这个错误，则可能是Android虚拟机网络出问题，需检查虚拟机网络

```Java
java.net.UnknownHostException: Unable to resolve host "": No address associated with hostname
```



如果返回了response，但是data为null，如下

```Java
I/Zeus/download: response：{"error_no":0,"error_msg":"","data":null}
```

则有可能

- 插件的版本列表中没有一个已生效或回滚中的插件
- 版本列表中的插件版本比宿主已经安装的插件版本低（或者相等），则不会下发插件
- 灰度不匹配



**针对最后一点，灰度不匹配做下说明**

在编辑插件版本时会有灰度梯度的设置，如下（这代表灰度梯度范围在0-37）

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=OTJlZWY5MTQ3NTIzZDQ4NTk4NjdlMGUzZjhmYWQ0MzNfdkhLVVVYV3Rvb3pSZFdjRkZNd1I5cWNBMndtSTRiMFNfVG9rZW46Ym94Y25yMllCTzM1NkNqMGpUcXJ5d1M0akljXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)

如果你的did（设备id）的最后两位数不在灰度梯度范围内，则不会向此设备下发插件



**如何查看did**

1、在宿主首页会显示did（可能会要求你重启一遍才会显示）

![img](https://bytedance.feishu.cn/space/api/box/stream/download/asynccode/?code=OGNhZmE0MTNiYzhhZjM4ZGY2NGQwYWQ5NDMyNDIxOGFfWXJrb2JEOTdrSkJKWTV4TkNVc2UzQm5Mdm1aWm1jZFNfVG9rZW46Ym94Y252TXQ5Z2pmbWVndlNheXU4UHpjYnpmXzE2NDAyNjQ4MTg6MTY0MDI2ODQxOF9WNA)

2、打开logcat，用"did"筛选日志（包括引号），可以查看到包含did值的日志