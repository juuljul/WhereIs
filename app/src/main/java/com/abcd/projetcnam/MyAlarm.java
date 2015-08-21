package com.abcd.projetcnam;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class MyAlarm extends Activity
{

    private PendingIntent pendingIntent;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_alarm);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.DAY_OF_MONTH, 21);

        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        Intent myIntent = new Intent(MyAlarm.this, AlarmReceiver.class);
        //pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, 0, myIntent,0);
        pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, 3, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

    } //end onCreate
}
