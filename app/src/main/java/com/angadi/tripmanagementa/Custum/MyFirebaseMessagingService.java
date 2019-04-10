package com.angadi.tripmanagementa.Custum;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Activities.Imagesactivity;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    public static final String NOTIFICATION_REPLY = "NotificationReply";
    public static final String KEY_INTENT_MORE = "keyintentmore";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("remotemsg", ""+remoteMessage.getNotification());

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("KEY", "YOUR VAL");
        intent.putExtra("ChatId", remoteMessage.getData().get(Keys.chat_id));
        intent.putExtra("Name", remoteMessage.getData().get("sender_name"));
        intent.putExtra("UniqueID", remoteMessage.getData().get("sender_unique_id"));
        intent.putExtra("Avatar", remoteMessage.getData().get("sender_avatar"));

        Log.e("remotemsg1", ""+remoteMessage.getData().get(Keys.chat_id));


        //We need this object for getting direct input from notification
        RemoteInput remoteInput = new RemoteInput.Builder(NOTIFICATION_REPLY)
                .build();


        RemoteInput more = new RemoteInput.Builder(KEY_INTENT_MORE)
                .build();


        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Action reply =
                new NotificationCompat.Action.Builder(R.mipmap.reply,
                        "Reply", pendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        NotificationCompat.Action mark_as_read =
                new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_more,
                        "Mark read", pendingIntent)
                        .addRemoteInput(more)
                        .build();


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.app_icon)
                .addAction(reply)
                .addAction(mark_as_read)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Log.e("Body", ""+remoteMessage.getNotification().getBody());
        Log.e("Title", ""+remoteMessage.getNotification().getTitle());
        Log.e("Data", ""+remoteMessage.getData());
        Log.e("remotemsg123", ""+remoteMessage.getData());

        //Log.e("remotemsg1234", ""+remoteMessage.getData().);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }



    private int getNotificationIcon() {
        boolean whiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        return whiteIcon ? R.mipmap.app_icon : R.mipmap.app_icon;
    }



}