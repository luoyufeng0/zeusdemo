package com.volcengine.zeus.plugin1_impl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;


/**
 * @author xuekai
 * @date 2021/6/1
 */
public class LivePluginDemoAppCompatActivity extends AppCompatActivity {
    private static final String TAG = "LivePluginDemoAppCompat";
    public static final String AUTHORITY = "com.volcengine.zeusdemo.zeus.provider.demo";
    public static final Uri CONTENT_URI_FIRST = Uri.parse("content://" + AUTHORITY + "/books");
    public static Uri mCurrentURI = CONTENT_URI_FIRST;
    public static final Uri CONTENT_URI_FIRST1 = Uri.parse("content://" + AUTHORITY + "/books/1");
    public static Uri mCurrentURI1 = CONTENT_URI_FIRST1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin1);
        String string = getString(R.string.pluginstring);
        Log.d(TAG, "app = " + getApplication());
        Log.d(TAG, "query id = " + R.id.query);
        Log.d(TAG, "query id 反射= " + getResources().getIdentifier("query", "id", "写死为穿山甲包名"));
        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMetrics dm = LivePluginDemoAppCompatActivity.this.getResources().getDisplayMetrics();
                DisplayMetrics dm1 = v.getResources().getDisplayMetrics();
                System.out.println("宽度：" + dm.widthPixels + "  " + dm1.widthPixels);
            }
        });
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });
        findViewById(R.id.getType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getType();
            }
        });
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

    }

    private void call() {
        ContentResolver resolver = getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Bundle extras = new Bundle();
            extras.putString("pangle", "sss6666");
            resolver.call(mCurrentURI.getAuthority(), "call", "6666", extras);
        } else {
            Bundle extras = new Bundle();
            extras.putString("pangle", "sss8888");
            resolver.call(mCurrentURI, "call2", "8888", extras);
        }
    }

    private void deleteData() {
        ContentResolver resolver = getContentResolver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Bundle extras = new Bundle();
            extras.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, "_id=?");
            extras.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, new String[]{String.valueOf(1)});
            final int delete = resolver.delete(mCurrentURI, extras);
            Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->使用Bundle参数-执行删除操作，count=" + delete);
        } else {
            final int delete = resolver.delete(mCurrentURI, "_id=?", new String[]{String.valueOf(1)});
            Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->执行删除操作，count=" + delete);
        }
    }

    private void updateData() {
        ContentResolver resolver = getContentResolver();
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", "name-pangle-ss");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //使用bundle参数
            Bundle extras = new Bundle();
            extras.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, "_id=?");
            extras.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, new String[]{String.valueOf(1)});
            final int update = resolver.update(mCurrentURI, initialValues, "_id=?", new String[]{String.valueOf(1)});
            Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->使用Bundle参数-执行更新操作，count=" + update);
        } else {
            final int update = resolver.update(mCurrentURI, initialValues, "_id=?", new String[]{String.valueOf(1)});
            Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->执行更新操作，count=" + update);
        }

    }

    private void getType() {
        ContentResolver resolver = getContentResolver();
        final String type = resolver.getType(mCurrentURI);
        Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->type=" + type);
    }

    @SuppressLint("Recycle")
    public void QureyData() {
        String name = null;
//        Cursor cursor = getContentResolver().query(mCurrentURI, null, null, null, null);
        Cursor cursor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cursor = getContentResolver().query(mCurrentURI, null, null, null);
            Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->Qurey(4个参数)-使用bundle参数");
        } else {
            cursor = getContentResolver().query(mCurrentURI, null, null, null, null);
            Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->Qurey(5个参数)");
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("name"));
                Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->QureyData-name=" + name);
            }
        }
    }

    public void InsertData() {
        Random random = new Random();


        for (int i = 0; i < 3; i++) {
            String editName = "pangle_sdk-" + random.nextInt();
            ContentValues values = new ContentValues();
            values.put("name", editName);
            values.put("author", editName);
            Uri result = null;
            //API 30
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                result = getContentResolver().insert(mCurrentURI, values, new Bundle());
            } else {
                result = getContentResolver().insert(mCurrentURI, values);
            }
            if (result == null) return;
            long parseid = ContentUris.parseId(result);
            Log.d("Zeus/provider", "LivePluginDemoAppCompatActivity->执行插入操作，count=" + parseid);
        }

//      注意 ： 此条添加上才ContentObserver可以监听数据库改变
        //插件化思路：注册给 proxy -> proxy注册,通过本地过滤 authority,决定通知那个注册者；
//        getContentResolver().notifyChange(mCurrentURI, null);

    }

    private final String receiverPermissions = "com.volcengine.plugin.action.2";
    PanglePluginReceiver receiver1;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registerReceiverClick3(View view) {
        receiver1 = new PanglePluginReceiver();
        IntentFilter intentFilter = new IntentFilter();
        HandlerThread handlerThread = new HandlerThread("thread-handle");
        handlerThread.start();
        Handler h = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.d("Zeus/receiver", "handleMessage,,,,,,");
            }
        };
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("com.volcengine.plugin.action.[BroadcastReceiver,IntentFilter,BroadcastPermission,Handler,Int]");
        registerReceiver(receiver1, intentFilter, "com.xk.sdkhotupdate.openadsdk.permission.TT_PANGOLIN", h, RECEIVER_VISIBLE_TO_INSTANT_APPS);
        Toast.makeText(this, "registerReceiverClick[BroadcastReceiver,IntentFilter,BroadcastPermission,Handler,Int]", Toast.LENGTH_SHORT).show();
    }

    public void sendBC3(View view) {
        Intent intent = new Intent();
        intent.setAction("com.volcengine.plugin.action.[BroadcastReceiver,IntentFilter,BroadcastPermission,Handler,Int]");
        sendBroadcast(intent, "com.xk.sdkhotupdate.openadsdk.permission.TT_PANGOLIN");
        Toast.makeText(this, "sendBroadcast(Intent,Permission)", Toast.LENGTH_SHORT).show();
    }

    PanglePluginReceiver receiver2;
    public void registerReceiverClick(View view) {

        receiver2 = new PanglePluginReceiver();
        IntentFilter intentFilter = new IntentFilter();
        HandlerThread handlerThread = new HandlerThread("thread-handle");
        handlerThread.start();
        Handler h = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.d("Zeus/receiver", "handleMessage,,,,,,");
            }
        };
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("com.volcengine.plugin.action.[BroadcastReceiver,IntentFilter,BroadcastPermission,Handler]");
        registerReceiver(receiver2, intentFilter, "com.xk.sdkhotupdate.openadsdk.permission.TT_PANGOLIN", h);
        Toast.makeText(this, "registerReceiverClick[BroadcastReceiver,IntentFilter,BroadcastPermission,Handler]", Toast.LENGTH_SHORT).show();
    }

    public void sendBC(View view) {
        Intent intent = new Intent();
        intent.setAction("com.volcengine.plugin.action.[BroadcastReceiver,IntentFilter,BroadcastPermission,Handler]");
        sendBroadcast(intent, "com.xk.sdkhotupdate.openadsdk.permission.TT_PANGOLIN");
        Toast.makeText(this, "sendBroadcast(Intent,Permission)", Toast.LENGTH_SHORT).show();
    }
    PanglePluginReceiver receiver3;

    public void registerReceiverClick2(View view) {
        receiver3 = new PanglePluginReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.volcengine.plugin.action.[BroadcastReceiver,IntentFilter]");
        registerReceiver(receiver3, intentFilter);
        Toast.makeText(this, "registerReceiverClick[BroadcastReceiver,IntentFilter]", Toast.LENGTH_SHORT).show();
    }
    public void sendBC2(View view) {
        sendBroadcast(new Intent("com.volcengine.plugin.action.[BroadcastReceiver,IntentFilter]"));
        Toast.makeText(this, "sendBroadcast(Intent)", Toast.LENGTH_SHORT).show();
    }

    public void jump2Plugin2(View view) {
        Intent intent = new Intent(this, LivePluginDemoAppCompatActivity2.class);
        intent.putExtra("activity1", "activity1---");
        startActivityForResult(intent, 10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        String data1 = data.getStringExtra("test");
        String data1 = "";
        Toast.makeText(this, "onActivityResult request:" + requestCode + " result:" + resultCode + " data:" + data1, Toast.LENGTH_SHORT).show();
    }

    public void startLivePluginService(View v) {
        startService(new Intent(this, LivePluginService.class));
    }


    public void stopLivePluginService(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "stop from" + Thread.currentThread());
                stopService(new Intent(LivePluginDemoAppCompatActivity.this, LivePluginIntentService.class));
            }
        }).start();
    }

    private LivePluginIntentService.LivePluginBinder service1;

    ServiceConnection conn = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected   " + name);
            service1 = (LivePluginIntentService.LivePluginBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected   " + name);
            service1 = null;
        }
    };

    public void bindLivePluginService(View view) {
        bindService(new Intent(this, LivePluginIntentService.class), conn, 0);
    }

    public void unbindLivePluginService(View view) {
        unbindService(conn);
    }

    public void play(View view) {
        if (service1 != null) {
            service1.play();
        }
    }

    boolean isFirst = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
            }
            isFirst = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver1 != null){
            unregisterReceiver(receiver1);
        }

        if (receiver2 != null) {
            unregisterReceiver(receiver2);
        }

        if (receiver3 != null) {
            unregisterReceiver(receiver3);
        }
        Toast.makeText(this, "Activity onDestroy", Toast.LENGTH_SHORT).show();

    }
}
