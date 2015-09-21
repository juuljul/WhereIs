package com.abcd.projetcnam;

import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends ActionBarActivity {


    /*TextView textSalle, textMaps;
    Button buttonSalle, buttonMaps;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*textSalle = (TextView) findViewById(R.id.textSalle);
        textMaps = (TextView) findViewById(R.id.textMaps);
        buttonSalle = (Button) findViewById(R.id.buttonSalle);*/
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

    public void findSalle(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    public void goToMap(View view) {
        Intent intent=null, chooser=null;
        intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:48.8669921,2.3545226"));
        chooser= Intent.createChooser(intent, "Launch Maps");
        startActivity(chooser);
    }
}
