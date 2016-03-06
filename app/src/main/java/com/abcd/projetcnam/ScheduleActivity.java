package com.abcd.projetcnam;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class ScheduleActivity extends ActionBarActivity {

    ListView listView;
    MyBaseAdapter myBaseAdapter;
    public static final int AJOUTER_EVENEMENT_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        listView = (ListView) findViewById(R.id.listView);
        myBaseAdapter = new MyBaseAdapter(this);
        listView.setAdapter(myBaseAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToSchedulePlanner(View view) {
        Intent intent = new Intent(this, MyAlarm.class);
        startActivityForResult(intent,AJOUTER_EVENEMENT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AJOUTER_EVENEMENT_CODE){
            if (RESULT_OK == resultCode){
                myBaseAdapter = new MyBaseAdapter(this);
                listView.setAdapter(myBaseAdapter);
            }
        }
    }
}
