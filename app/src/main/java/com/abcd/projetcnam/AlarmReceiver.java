package com.abcd.projetcnam;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        /*// TODO Auto-generated method stub
        String phoneNumberReciver="987654321";
        String message="Hi I will be there later, See You Soon";
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
        Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG);*/

        /*for (int j=0;j<MyAlarm.i;j++){
            Intent service1 = new Intent(context, MyAlarmService.class);
            context.startService(service1);

        }*/

        Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);




        /*Log.i("App", "called receiver method");
        try{
            Utils.generateNotification(context);
        }catch(Exception e){
            e.printStackTrace();
        }*/

    }

}
