package com.william.calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

public class AlarmNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentTitle("Đã tới giờ tập luyện")
                .setContentText("Hãy quay trở lại và nâng cao thể lực của bản thân nào")
                .setContentInfo("Info")
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());
    }
}
