package com.abcd.projetcnam;

import android.content.Intent;
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


public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener {

    EditText editStartRoom, editFinishRoom;
    TextView textStartRoom, textFinishRoom;
    Button button;
    String startRoom, stopRoom= "";

    /*Graph graph;
    int startIndex = 0;
    int stopIndex =0;
    String st = "";*/

    private TextToSpeech textToSpeech;
    private Locale currentSpokenLang = Locale.FRENCH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFinishRoom = (EditText) findViewById(R.id.editFinishRoom);
        editStartRoom = (EditText) findViewById(R.id.editStartRoom);
        textFinishRoom = (TextView) findViewById(R.id.textFinishRoom);
        textStartRoom = (TextView) findViewById(R.id.textStartRoom);
        button = (Button) findViewById(R.id.button);

        textToSpeech = new TextToSpeech(this, this);
        //graph = new Graph();

    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
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
        startRoom = editStartRoom.getText().toString();
        stopRoom = editFinishRoom.getText().toString();

        /*
        for (Node node :graph.getNodes()){
            if(node.getRoomName().equals(startRoom)){
                startIndex = node.getIndex();
                break;
            }
        }
        for (Node node :graph.getNodes()) {
            if (node.getRoomName().equals(stopRoom)) {
                stopIndex = node.getIndex();
                break;
            }
        }

        graph.findMinimumDistance(startIndex,stopIndex);
        for (int i=graph.getFinalPath().size()-1; i>=0 ;i--){
            st = st + " >> " + graph.getFinalPath().get(i);
        }
        Toast.makeText(this, st, Toast.LENGTH_LONG).show();
        st = "";*/


        Intent intent = new Intent(this, TrajetActivity.class);
        intent.putExtra("StartRoom",startRoom);
        intent.putExtra("StopRoom",stopRoom);
        intent.putExtra("DynamicPlan",true);
        startActivity(intent);

    }

    public void goToAlarm(View view) {
        Intent intent = new Intent(this, DestinationActivity.class);
        startActivity(intent);
    }

    public void goToAlarmCreator(View view) {
        Intent intent = new Intent(this, MyAlarm.class);
        startActivity(intent);
    }

    public void speakText(View view) {

        // Set the voice to use
        textToSpeech.setLanguage(currentSpokenLang);

        // Check that translations are in the array
        /*if (arrayOfTranslations.length >= 9){

            // There aren't voices for our first 3 languages so skip them
            // QUEUE_FLUSH deletes previous text to read and replaces it
            // with new text
            textToSpeech.speak(arrayOfTranslations[spinnerIndex+4], TextToSpeech.QUEUE_FLUSH, null);

        } else {

            Toast.makeText(this, "Translate Text First", Toast.LENGTH_SHORT).show();

        }*/
        textToSpeech.speak(editStartRoom.getText().toString(),TextToSpeech.QUEUE_FLUSH, null);



    }

    @Override
    public void onInit(int status) {
        // Check if TextToSpeech is available
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(currentSpokenLang);

            // If language data or a specific language isn't available error
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language Not Supported", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Text To Speech Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoStaticPLan(View view) {
        Intent intent = new Intent(this, TrajetActivity.class);
        startActivity(intent);
    }
}
