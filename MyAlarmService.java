package com.abcd.projetcnam;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class MyAlarmService extends Service
{
    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this.getApplicationContext(),MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,MyAlarm.i,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Where is My Cnam ?")
                .setContentText("N'oubliez pas votre examen au Cnam dans exactement 2 heures")
                .setSmallIcon(R.drawable.triang30vert)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(MyAlarm.i,notification);
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}