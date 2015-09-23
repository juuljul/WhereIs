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
        toggleAlarm = (ToggleButton) findViewById(R.id.toggleAlarm);

        // A remplacer éventuellement par un autocompleteTextView
        spinner = (Spinner) findViewById(R.id.spinner);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.salles, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }



    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
            cal.set( Calendar.YEAR, year );
            cal.set( Calendar.MONTH, monthOfYear );
            cal.set( Calendar.DAY_OF_MONTH, dayOfMonth );
            timePicker.show();
        }
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet( TimePicker view, int hour, int minute ) {
            cal.set( Calendar.HOUR, hour );
            cal.set( Calendar.MINUTE, minute );
            updateDateTime();
        }
    };

    public void dateOnClick( View view ) {
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

    public void registerData(View view) {
        dbAdapter = new DbHelperAdapter(this);
        //On ajoute l'évènement à la base de données
        dbAdapter.insertData(editMatiere.getText().toString(),spinner.getSelectedItem().toString(),
                editDate.getText().toString());

        // Si le toggleAlarm est enclenché on ajoute une alerte 2 heures avant l'évènement
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
    }

    public void visitData(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}
