package com.abcd.projetcnam;

        import java.util.GregorianCalendar;

        import android.app.Notification;
        import android.app.NotificationManager;
        import android.content.BroadcastReceiver;
        import android.content.IntentFilter;
        import android.os.Bundle;
        import android.app.Activity;
        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.view.Menu;
        import android.view.View;
        import android.widget.Toast;

public class AlarmCreator extends Activity {

    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_creator);



    }
    public void scheduleAlarm(View V){

        br = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {

                createNotification();
            }
        };
        // Register the receiver and create the intents for passing information
        //registerReceiver( br, new IntentFilter( "com.example.FirstAndroid" ) );
        registerReceiver( br, new IntentFilter( "com.abcd.projetcnam.AlarmCreator") );

        Long time= new GregorianCalendar().getTimeInMillis()+30*1000;
        Intent intentAlarm= new Intent(this, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm Scheduled for Tomorrow", Toast.LENGTH_LONG).show();
    }




    private void createNotification() {
        // prepare intent which is triggered if the notification is selected
        Intent intent = new Intent( this, MedicineAlarm.class );
        PendingIntent pIntent = PendingIntent.getActivity( this, 0, intent, 0 );
        Notification n = new Notification.Builder( this )
                .setContentTitle( "Medicine Alarm" )
                .setContentText( "Notes" )
                .setSmallIcon( R.drawable.triang30vert )
                .setContentIntent( pIntent )
                .setAutoCancel( true )
                .addAction( R.drawable.triang30vert, "Call", pIntent )
                .addAction( R.drawable.triang30vert, "More", pIntent )
                .build();
        NotificationManager notificationManager =
                (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
        notificationManager.notify( 0, n );
    }




    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

}
