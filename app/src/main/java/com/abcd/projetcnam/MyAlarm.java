package com.abcd.projetcnam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MyAlarm extends Activity
{

    PendingIntent pendingIntent;
    Uri sound;

    private Button button;
    private EditText editMatiere;
    private EditText editDate;
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    TextView matiereText, dateText, roomText;
    Calendar cal;
    TimePickerDialog timePicker;
    static AlarmManager alarmManager;
    public static int i=0;

    ToggleButton toggleAlarm;

    ArrayList <Calendar> calendarArrayList = new ArrayList<>();
    //ArrayList<PendingIntent> pendingIntentArrayList = new ArrayList<>();

    DbHelperAdapter dbAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_alarm);

        editDate = (EditText) findViewById(R.id.editDate);
        editMatiere = (EditText) findViewById(R.id.editMatiere);
        matiereText = (TextView) findViewById(R.id.matiereText);
        dateText = (TextView) findViewById(R.id.dateText);
        roomText = (TextView) findViewById(R.id.roomText);
        //button = (Button) findViewById(R.id.button);
        toggleAlarm = (ToggleButton) findViewById(R.id.toggleAlarm);


        // A remplacer éventuellement par un autocompleteTextView
        spinner = (Spinner) findViewById(R.id.spinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.salles, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        //cal = Calendar.getInstance();


        /*Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.DAY_OF_MONTH, 21);

        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.AM);

        sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent myIntent = new Intent(MyAlarm.this, AlarmReceiver.class);
        //pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, 0, myIntent,0);
        pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, 3, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),60*60*24*1000,pendingIntent);*/

    } //end onCreate



    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
            //Alarm am = (Alarm)currentAlarm;

            cal.set( Calendar.YEAR, year );
            cal.set( Calendar.MONTH, monthOfYear );
            cal.set( Calendar.DAY_OF_MONTH, dayOfMonth );

            timePicker.show();  // Launches the TimePicker right after the DatePicker closes
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet( TimePicker view, int hour, int minute ) {
            //Alarm am = (Alarm)currentAlarm;
            cal.set( Calendar.HOUR, hour );
            cal.set( Calendar.MINUTE, minute );
            updateDateTime();
        }
    };

    public void dateOnClick( View view ) {
        //Alarm am = (Alarm)view.getTag();
        //currentAlarm = am;
        cal = Calendar.getInstance();

        timePicker = new TimePickerDialog(this, time,
                cal.get( Calendar.HOUR ),
                cal.get( Calendar.MINUTE ), false );
        new DatePickerDialog(this, date,
                cal.get( Calendar.YEAR ),
                cal.get( Calendar.MONTH ),
                cal.get( Calendar.DAY_OF_MONTH ) ).show();
    }


    public void updateDateTime() {
        String myFormat = "MM-dd-yy hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat( myFormat, Locale.US );
        editDate.setText( sdf.format( cal.getTime() ) );
    }


   /* public void activateAlarm(View view) {


        i++;
        calendarArrayList.add(cal);


        //sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent myIntent = new Intent(MyAlarm.this, AlarmReceiver.class);

        myIntent.setData(Uri.parse("alarm:" + i));
        //myIntent.setAction(String.valueOf(i));

        pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, i, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);
        //pendingIntentArrayList.add(pendingIntent);

       for (int j=0;j<i;j++){
            alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, calendarArrayList.get(j).getTimeInMillis(), pendingIntentArrayList.get(j));
        }

        //AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);



    }*/

    public void registerData(View view) {
        dbAdapter = new DbHelperAdapter(this);
        dbAdapter.insertData(editMatiere.getText().toString(),spinner.getSelectedItem().toString(),
                editDate.getText().toString());

        if (toggleAlarm.isChecked()){
            i++;
            calendarArrayList.add(cal);
            Intent myIntent = new Intent(MyAlarm.this, AlarmReceiver.class);
            myIntent.setData(Uri.parse("alarm:" + i));
            pendingIntent = PendingIntent.getBroadcast(MyAlarm.this, i, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            long timeAlarm = cal.getTimeInMillis()-2*3600*1000;
            alarmManager.set(AlarmManager.RTC, timeAlarm, pendingIntent);
        }

        Toast.makeText(this, "L'évènement a été ajouté à l'emploi du temps",Toast.LENGTH_LONG).show();

        /*Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);*/

    }

    public void visitData(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);

    }


}
