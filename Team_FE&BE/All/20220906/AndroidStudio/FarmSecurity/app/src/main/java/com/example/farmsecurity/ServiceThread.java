package com.example.farmsecurity;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.farmsecurity.Activity.Main;

public class ServiceThread extends Service {
    private Thread mThread;

    public void onCreate() {
        super.onCreate();
    }

    public ServiceThread() {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //Foreground 서비스가 실행되는 동안 디바이스 상단에 서비스 실행 중을 알리는 알림 표시
    private void startForegroundService() {
        Log.d("FCM Log : ", "Foreground Start");

        NotificationChannel channel = null;
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            channel = new NotificationChannel("ForeGround", "ForeGround", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("포그라운드 알림 채널");
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "ForeGround");
        builder.setSmallIcon(R.drawable.bird);
        builder.setContentTitle("FarmSecurity");
        builder.setContentText("농장 감시중...");

        Intent notificationIntent = new Intent(this, Main.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(new NotificationChannel("ForeGround", "Main Channel", NotificationManager.IMPORTANCE_DEFAULT));
        }

        startForeground(1, builder.build());
        Log.d("FCM Log : ", "Foreground End");
    }

    //Foreground 서비스 실행
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if("startForeground".equals(intent.getAction())){
            startForegroundService();
        }

        return START_STICKY;
    }

}