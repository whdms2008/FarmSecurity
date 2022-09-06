package com.example.farmsecurity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token){
        Log.d("FCM Log", "Refreshed token: "+token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FCM Log : ", "MessageReceived");
        if(remoteMessage.getData().size()>0){
            sendNotification(remoteMessage.getData().get("body"),remoteMessage.getData().get("title"));
        }
    }

    private void sendNotification(String body, String title) {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("FarmSecurity", "FarmSecurity", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("푸시 알림 테스트용 채널");
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(this, channel.getId());

            builder.setSmallIcon(R.drawable.bird);
            builder.setContentTitle(title);
            builder.setContentText(body);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);

            notificationManager.notify(2, builder.build());
        }

    }
}
