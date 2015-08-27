package com.abcd.projetcnam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by julien on 11/08/2015.
 */
public class TrajetActivity extends Activity implements TextToSpeech.OnInitListener{
    String startRoom, stopRoom;

    String cheminSpeech = "";
    String chemin="";

    private TextToSpeech textToSpeech;
    private Locale currentSpokenLang = Locale.FRENCH;

    MySurfaceView mySurfaceView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null){
            startRoom = intent.getStringExtra("StartRoom");
            stopRoom = intent.getStringExtra("StopRoom");
        }
        mySurfaceView = new MySurfaceView(this, startRoom,stopRoom);
        setContentView(mySurfaceView);

        textToSpeech = new TextToSpeech(this,this);


        for (int i=mySurfaceView.getGraph().getFinalPath().size()-1; i>=0 ;i--){
            chemin = chemin +mySurfaceView.getGraph().getFinalPath().get(i)+", ";
        }

        cheminSpeech = "Pour atteindre l'accès numéro" + mySurfaceView.getGraph().getFinalPath().get(0) +
                "Vous devez successivement passer par les accès numéros" + chemin +
                "le chemin total se fait à la marche en " + mySurfaceView.getLongueurTrajet() + "pas";




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
    public boolean onTouchEvent(MotionEvent event) {

        textToSpeech.setLanguage(currentSpokenLang);
        textToSpeech.speak(cheminSpeech,TextToSpeech.QUEUE_FLUSH, null);
        return super.onTouchEvent(event);
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


    @Override
    public void onInit(int status) {

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
}
