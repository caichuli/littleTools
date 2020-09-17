package com.cjq.littletools.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TestService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Serivceb绑定！", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Serivce创建！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Serivce启动！", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Serivce销毁！", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
