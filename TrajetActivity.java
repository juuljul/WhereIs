package com.abcd.projetcnam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
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

    boolean isPlanDynamic = false;

    private TextToSpeech textToSpeech;
    private Locale currentSpokenLang = Locale.FRENCH;
    String cheminSpeech = "";
    String chemin="";

    MySurfaceView mySurfaceView;
    LinearLayout container, buttonsLayout;
    Button ttsBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null){
            startRoom = intent.getStringExtra("StartRoom");
            stopRoom = intent.getStringExtra("StopRoom");
            isPlanDynamic = intent.getBooleanExtra("DynamicPlan",false);
        }
        // Le linearlayout container occupe tout l'écran
        container = new LinearLayout(this);
        container.setWeightSum(1f);
        container.setOrientation(LinearLayout.VERTICAL);

        mySurfaceView = new MySurfaceView(this, startRoom,stopRoom,isPlanDynamic);
        // paramètres qui seront appliqués au surfaceview au sein du container
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
        p1.weight=0.9f;

        //paramètres qui seront appliqués au buttonslayout au sein du container
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
        p2.weight=0.1f;

        // le buttonslayout contient les 2 boutons, son orientation est horizontale
        buttonsLayout = new LinearLayout(this);
        buttonsLayout.setWeightSum(1f);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);

        ttsBtn = new Button(this);
        ttsBtn.setText("Ecouter le chemin détaillé");
        ttsBtn.setPadding(0,0,0,0);
        ttsBtn.setBackgroundResource(R.drawable.btn_degrade_blanc);

        backBtn = new Button(this);
        backBtn.setText("Rechercher un autre trajet");
        backBtn.setPadding(0,0,0,0);
        backBtn.setBackgroundResource(R.drawable.btn_degrade_blanc);

        // parametres qui seront appliqués aux deux boutons
        LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
        p3.weight=0.5f;
        p3.setMargins(10,0,10,0);

        // On ajoute les deux boutons au buttonslayout
        buttonsLayout.addView(ttsBtn,p3);
        buttonsLayout.addView(backBtn,p3);
        //On ajoute le surfaceview et le buttonslayout au container
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
        setContentView(container, linLayoutParam);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),LocationActivity.class);
                startActivity(intent);
            }
        });

        // Si le plan est dynamique, on initialise le Text To Speech et on constitue le String cheminSpeech qui va être dit
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(currentSpokenLang);

            // Si données de langage ou langage en particulier non disponibles, alors erreur
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Le langage n'est pas supporté", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "L'option Entendre le Trajet a échoué", Toast.LENGTH_SHORT).show();
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
}
