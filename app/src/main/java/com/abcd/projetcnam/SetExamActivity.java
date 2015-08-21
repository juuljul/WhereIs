package com.abcd.projetcnam;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

/**
 * Created by julien on 20/08/2015.
 */
public class SetExamActivity extends ActionBarActivity {

    PendingIntent pendingIntent;

    private ToggleButton toggleButton1;
    private EditText editNotes1;
    private EditText dateText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examsettings);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int i = sharedPreferences.getInt("numberOfLaunches", 1);

        if (i<2){
            alarmMethod();
            i++;
            editor.putInt("numberOfLaunches",i);
            editor.commit();
        }

    }

    private void alarmMethod() {
        Intent intent = new Intent(this, NotifyService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getService(this,0,intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,54);
        calendar.set(Calendar.HOUR,1);
        calendar.set(Calendar.AM_PM,Calendar.PM);
        //calendar.add(Calendar.DAY_OF_MONTH, 1);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),60*60*24*1000,pendingIntent);

        Toast.makeText(this, "StartAlarm", Toast.LENGTH_LONG).show();

    }
}
