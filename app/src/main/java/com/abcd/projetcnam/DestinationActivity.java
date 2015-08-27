package com.abcd.projetcnam;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class DestinationActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        spinner = (Spinner) findViewById(R.id.spinner);
        //arrayAdapter = ArrayAdapter.createFromResource(this,R.array.salles, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.salles, R.layout.spinner_layout);
        spinner.setAdapter(arrayAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_destination, menu);
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

    public void goToSchedule(View view) {
        Intent intent = new Intent(this,ScheduleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void goToLocator(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        String room = spinner.getSelectedItem().toString();
        intent.putExtra("RoomName",room);
        startActivity(intent);
    }
}
