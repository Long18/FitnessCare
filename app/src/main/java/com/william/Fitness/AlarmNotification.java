package com.william.Fitness;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

public class AlarmNotification extends BroadcastReceiver {

    public static final String CHANNEL_ID = "CHANNEL 1";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context,MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI).setContentTitle("Đã tới giờ tập luyện")
                .setContentText("Hãy quay trở lại và nâng cao thể lực của bản thân nào")
                .setContentInfo("Info")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
/*
        notification.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentTitle("Đã tới giờ tập luyện")
                .setContentText("Hãy quay trở lại và nâng cao thể lực của bản thân nào")
                .setContentInfo("Info")
                .setAutoCancel(true)
                .setContentIntent(PendingIntent)
                .build();*/

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null){
            notificationManager.notify(1, notification);
        }
        notificationManager.notify(0, notification);
    }

   

}
