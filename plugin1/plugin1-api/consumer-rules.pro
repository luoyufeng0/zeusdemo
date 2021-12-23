-keep class com.volcengine.zeus.activity.IPluginActivity {
	*;
}
-keepclassmembers class * {
	*** bindLivePluginService(android.view.View);
	*** jump2Plugin2(android.view.View);
	*** play(android.view.View);
	*** registerReceiverClick(android.view.View);
	*** registerReceiverClick2(android.view.View);
	*** registerReceiverClick3(android.view.View);
	*** sendBC(android.view.View);
	*** sendBC2(android.view.View);
	*** sendBC3(android.view.View);
	*** startLivePluginService(android.view.View);
	*** stopLivePluginService(android.view.View);
	*** unbindLivePluginService(android.view.View);
	*** getContext(...);
	*** getActivity(...);
	*** getResources(...);
	*** startActivity(...);
	*** startActivityForResult(...);
	*** registerReceiver(...);
	*** unregisterReceiver(...);
	*** query(...);
	*** getType(...);
	*** insert(...);
	*** delete(...);
	*** update(...);
	*** call(...);
	*** setResult(...);
	*** startService(...);
	*** stopService(...);
	*** bindService(...);
	*** unbindService(...);
	*** requestPermissions(...);
	*** getIdentifier(...);
}
-keep class com.volcengine.zeus.plugin.Plugin {
	*;
}
-keep class android.support.v4.app.FragmentActivity {
	*;
}
-keep class android.arch.lifecycle.LifecycleOwner {
	*;
}
-keep class android.support.v7.app.AppCompatCallback {
	*;
}
-keep class com.volcengine.zeus.plugin1_api.Plugin1 {
	*;
}
-keep class com.volcengine.zeus.ZeusApplication {
	*;
}
-keep class com.volcengine.zeus.service.PluginService {
	*;
}
-keep class android.support.v7.app.AppCompatActivity {
	*;
}
-keep class com.volcengine.zeus.plugin1_api.Plugin1Constant {
	*;
}
-keep class android.support.v4.app.TaskStackBuilder$SupportParentable {
	*;
}
-keep class android.support.v4.app.ActivityCompat$OnRequestPermissionsResultCallback {
	*;
}
-keep class com.volcengine.zeus.transform.ZeusProviderTransform {
	*;
}
-keep class com.volcengine.zeus.transform.ZeusTransformUtils {
	*;
}
-keep class android.arch.lifecycle.ViewModelStoreOwner {
	*;
}
-keep class com.volcengine.zeus.provider.PluginContentProvider {
	*;
}
-keep class com.volcengine.zeus.service.IZeusPluginService {
	*;
}
-keep class com.volcengine.zeus.activity.GeneratePluginAppCompatActivity {
	*;
}
-keep class com.volcengine.zeus.service.PluginIntentService {
	*;
}
-keep class android.support.v4.app.ActivityCompat$RequestPermissionsRequestCodeValidator {
	*;
}
-keep class com.volcengine.zeus.receiver.PluginBroadcastReceiver {
	*;
}
-keep class android.support.v4.view.KeyEventDispatcher$Component {
	*;
}
-keep class android.support.v7.app.ActionBarDrawerToggle$DelegateProvider {
	*;
}
-keep class com.volcengine.zeus.Zeus {
	*;
}
-keep class com.volcengine.zeus.plugin1_api.IPlugin1Api {
	*;
}
