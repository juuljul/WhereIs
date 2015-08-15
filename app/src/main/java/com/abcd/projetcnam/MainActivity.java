package com.abcd.projetcnam;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    EditText editStartRoom, editFinishRoom;
    TextView textStartRoom, textFinishRoom;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFinishRoom = (EditText) findViewById(R.id.editFinishRoom);
        editStartRoom = (EditText) findViewById(R.id.editStartRoom);
        textFinishRoom = (TextView) findViewById(R.id.textFinishRoom);
        textStartRoom = (TextView) findViewById(R.id.textStartRoom);
        button = (Button) findViewById(R.id.button);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void validateRooms(View view) {
        String startRoom = editStartRoom.getText().toString();
        String stopRoom = editFinishRoom.getText().toString();
        Intent intent = new Intent(this, TrajetActivity.class);
        intent.putExtra("StartRoom",startRoom);
        intent.putExtra("StopRoom",stopRoom);
        startActivity(intent);

    }
}
