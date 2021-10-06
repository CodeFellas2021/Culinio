package com.example.cibo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PuchNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        System.out.println("inside pushnitification");
//        String text = remoteMessage.getNotification().getBody();
        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
        String authorizedEntity = "cibo-47d2b"; // Project id from Google Developer Console
        String scope = "GCM"; // e.g. communicating using GCM, but you can use any
//         URL-safe characters up to a maximum of 1000, or
//         you can also leave it blank.

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, "Heads Up Notification", NotificationManager.IMPORTANCE_HIGH);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

        Intent intent2 = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent2, 0);

        Intent intent = new Intent(PuchNotificationService.this, cancel_alarm.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent, pendingIntent1;

        pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, CHANNEL_ID).setContentTitle("Culinio").setContentText("Your order is ready!").setSmallIcon(R.drawable.cuilinio_txt).setAutoCancel(true).setContentIntent(pendingIntent).setDeleteIntent(pendingIntent2);
        }
        NotificationManagerCompat.from(this).notify(1, notification.build());

        super.onMessageReceived(remoteMessage);

        Intent s = new Intent(PuchNotificationService.this, sound_service.class);
        startService(s);

//        String title = remoteMessage.getNotification().getTitle();
//        String text = remoteMessage.getNotification().getBody();
//        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
//        String authorizedEntity = "cibo-47d2b"; // Project id from Google Developer Console
//        String scope = "GCM"; // e.g. communicating using GCM, but you can use any
//        // URL-safe characters up to a maximum of 1000, or
//        // you can also leave it blank.
//
//        NotificationChannel channel = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            channel = new NotificationChannel(CHANNEL_ID, "Heads Up Notification", NotificationManager.IMPORTANCE_HIGH);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            getSystemService(NotificationManager.class).createNotificationChannel(channel);
//        }
//        Notification.Builder notification = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            notification = new Notification.Builder(this, CHANNEL_ID).setContentTitle(title).setContentText(text).setSmallIcon(R.drawable.ic_launcher_background).setAutoCancel(true);
//        }
//        NotificationManagerCompat.from(this).notify(1, notification.build());
//        super.onMessageReceived(remoteMessage);
    }


}


