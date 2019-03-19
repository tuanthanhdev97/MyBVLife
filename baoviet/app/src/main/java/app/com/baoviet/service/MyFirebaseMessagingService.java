package app.com.baoviet.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import app.com.baoviet.InitialNotificationActivity;
import app.com.baoviet.R;
import app.com.baoviet.constant.Keys;


/**
 * Created by macosmv on 5/31/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private String notifyId;
    private String notifySendType;
    private String notifyUserCode;
    private String notifyType;
    private String notifyTypeDescription;
    private String notifyStatus;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        notifyId = data.get(Keys.KEY_NOTIFY_ID).toString();
        notifySendType = data.get(Keys.KEY_NOTIFY_SEND_TYPE).toString();
        notifyUserCode = data.get(Keys.KEY_NOTIFY_USER_CODE).toString();
        notifyType = data.get(Keys.KEY_NOTIFY_TYPE).toString();
        notifyTypeDescription = data.get(Keys.KEY_NOTIFY_TYPE_DESCRIPTION).toString();
        notifyStatus = data.get(Keys.KEY_NOTIFY_STATUS).toString();
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title, String messageBody) {
//        Intent intent = new Intent(getApplicationContext(), InitialNotificationActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(intent);
//        PendingIntent pendingIntent = stackBuilder
//                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intent = new Intent(getBaseContext(), InitialNotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_arrow_up)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
