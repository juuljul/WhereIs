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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by julien on 11/08/2015.
 */
public class TrajetActivity extends Activity implements TextToSpeech.OnInitListener{
    String startRoom, stopRoom;

    String cheminSpeech = "";
    String chemin="";

    boolean isPlanDynamic = false;

    private TextToSpeech textToSpeech;
    private Locale currentSpokenLang = Locale.FRENCH;

    MySurfaceView mySurfaceView;

    LinearLayout container, buttonsLayout;

    Button ttsBtn, backBtn;

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
        //mySurfaceView = new MySurfaceView(this, startRoom,stopRoom,isPlanDynamic);
        //setContentView(mySurfaceView);
        //setContentView(R.layout.activity_trajet);
        //mySurfaceView = (MySurfaceView) findViewById(R.id.mySurfaceView);
        //mySurfaceView = new MySurfaceView(this, startRoom,stopRoom,isPlanDynamic);
        /* button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);*/

       /* mySurfaceView.setStartRoom(startRoom);
        mySurfaceView.setStopRoom(stopRoom);
        mySurfaceView.setPlanDynamic(isPlanDynamic);*/

        container = new LinearLayout(this);
        container.setWeightSum(1f);
        container.setOrientation(LinearLayout.VERTICAL);

        mySurfaceView = new MySurfaceView(this, startRoom,stopRoom,isPlanDynamic);
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
        p1.weight=0.9f;

        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
        p2.weight=0.1f;

        buttonsLayout = new LinearLayout(this);
        buttonsLayout.setWeightSum(1f);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);

        ttsBtn = new Button(this);
        ttsBtn.setText("Ecouter le chemin détaillé");
        ttsBtn.setBackgroundResource(R.drawable.btn_degrade_blanc);

        backBtn = new Button(this);
        backBtn.setText("Rechercher un autre trajet");
        backBtn.setBackgroundResource(R.drawable.btn_degrade_blanc);


        LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
        p3.weight=0.5f;
        p3.setMargins(10,0,10,0);

        buttonsLayout.addView(ttsBtn,p3);
        buttonsLayout.addView(backBtn,p3);

        container.addView(mySurfaceView,p1);
        container.addView(buttonsLayout,p2);

        ttsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlanDynamic){
                    textToSpeech.setLanguage(currentSpokenLang);
                    textToSpeech.speak(cheminSpeech,TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });



        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // set LinearLayout as a root element of the screen
        setContentView(container, linLayoutParam);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),LocationActivity.class);
                startActivity(intent);
            }
        });

        /*LinearLayout container = new LinearLayout(context);
// some code ...
        container.setWeightSum(1f);

        View v1 = new View(context);
        v1.setBackgroundColor(Color.parseColor("#ff0000"));
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p1.weight=0.1f;

        View v2 = new View(context);
        v2.setBackgroundColor(Color.parseColor("#000000"));
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p2.weight=0.9f;

        container.addView(v1,p1);
        container.addView(v2,p2);*/



       if (isPlanDynamic){
            textToSpeech = new TextToSpeech(this,this);

            for (int i=mySurfaceView.getGraph().getFinalPath().size()-1; i>=0 ;i--){
                chemin = chemin +mySurfaceView.getGraph().
                        getNodes()[mySurfaceView.getGraph().getFinalPath().get(i)].getRoomName()
                        +", ";
            }

            cheminSpeech = "Pour atteindre l'accès numéro" + mySurfaceView.getGraph().
                            getNodes()[mySurfaceView.getGraph().getFinalPath().get(0)].getRoomName() +
                    "Vous devez successivement passer par les accès numéros" + chemin +
                    "le chemin total se fait à la marche en " + (int)mySurfaceView.getLongueurTrajet() + "pas";
        }

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
        if (isPlanDynamic){
            if (textToSpeech != null) {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
        }
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

    public void goToLocation(View view) {
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);
    }

    public void activateSound(View view) {
        if (isPlanDynamic){
            textToSpeech.setLanguage(currentSpokenLang);
            textToSpeech.speak(cheminSpeech,TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    /*public void goToOther(View view) {
        Intent intent = new Intent(this,LocationActivity.class);
        startActivity(intent);
    }*/
}
