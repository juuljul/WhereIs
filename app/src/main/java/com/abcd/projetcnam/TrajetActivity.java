package com.abcd.projetcnam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by julien on 11/08/2015.
 */
public class TrajetActivity extends Activity {
    String startRoom, stopRoom;

    String cheminSpeech = "";
    String chemin="";

    boolean isPlanDynamic = false;

    /*private TextToSpeech textToSpeech;
    private Locale currentSpokenLang = Locale.FRENCH;*/

    MySurfaceView mySurfaceView;

    /*Button button1,button2;
    Display display;
    Point size;

    int width = 0;
    int height = 0;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null){
            startRoom = intent.getStringExtra("StartRoom");
            stopRoom = intent.getStringExtra("StopRoom");
            isPlanDynamic = intent.getBooleanExtra("DynamicPlan",false);
        }
        mySurfaceView = new MySurfaceView(this, startRoom,stopRoom,isPlanDynamic);
        setContentView(mySurfaceView);
        /*setContentView(R.layout.activity_trajet);
        mySurfaceView = (MySurfaceView) findViewById(R.id.mySurfaceView);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);*/


       /* if (isPlanDynamic){
            textToSpeech = new TextToSpeech(this,this);


            for (int i=mySurfaceView.getGraph().getFinalPath().size()-1; i>=0 ;i--){
                chemin = chemin +mySurfaceView.getGraph().getFinalPath().get(i)+", ";
            }

            cheminSpeech = "Pour atteindre l'accès numéro" + mySurfaceView.getGraph().getFinalPath().get(0) +
                    "Vous devez successivement passer par les accès numéros" + chemin +
                    "le chemin total se fait à la marche en " + mySurfaceView.getLongueurTrajet() + "pas";

        }*/

        /*display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);

        width=size.x;
        height= size.y;

        button = new Button(this);

        button.setBackgroundResource(R.drawable.triang30vert);
        //button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        button.setText("Button ");
        button.setX(25 * width / 48);
        button.setY(7*height/26);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1;
                intent1 = new Intent(this,LocationActivity.class);
                startActivity(intent1);
            }
        });

        button1.setX(25 * width / 48);
        button1.setY(7*height/26);
        button2.setX(21 * width / 48);
        button2.setY(15*height/26);*/



    }

    @Override
    protected void onDestroy() {
        /*if (isPlanDynamic){
            if (textToSpeech != null) {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
        }*/
        super.onDestroy();
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isPlanDynamic){
            textToSpeech.setLanguage(currentSpokenLang);
            textToSpeech.speak(cheminSpeech,TextToSpeech.QUEUE_FLUSH, null);
        }
        return super.onTouchEvent(event);
    }*/

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


    /*@Override
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


    }*/

    /*public void goToOther(View view) {
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);
    }*/
}
