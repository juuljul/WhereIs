package com.abcd.projetcnam;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import java.net.URI;

/**
 * Created by julien on 20/08/2015.
 */
public class NotifyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this.getApplicationContext(),SetExamActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Mon titre")
                .setContentText("Mon texte")
                .setSmallIcon(R.drawable.triang30vert)
                .setContentIntent(pendingIntent)
                .addAction(0, "Load Website",pendingIntent)
                .build();
        notificationManager.notify(1,notification);

    }


}
