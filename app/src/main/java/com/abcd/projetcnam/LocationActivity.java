package com.abcd.projetcnam;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.PrintWriter;


public class LocationActivity extends ActionBarActivity implements ChoixDepartFragment.OnDepartSelected, ChoixArriveeFragment.OnArriveeSelected {

    String roomDestination;
    int indexDestination;
    ArrayAdapter arrayAdapter;
    Graph graph;
    //static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    String departText, arriveeText = "...";
    boolean departChoisi, arriveeChoisie = false;
    String findTrajetText = "Chercher le trajet le plus court de   à   ";
    Button buttonTrajet;
    Fragment choixDepartFragment;
    ChoixArriveeFragment choixArriveeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        buttonTrajet = (Button) findViewById(R.id.buttonTrajet);
        buttonTrajet.setEnabled(false);

        FragmentManager fragmentManager = getFragmentManager();
        choixDepartFragment = new ChoixDepartFragment();
        //choixArriveeFragment = new ChoixArriveeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.relativeLocation, choixDepartFragment);
        //fragmentTransaction.add(R.id.relativeDestination,choixArriveeFragment);
        fragmentTransaction.commit();

        graph = new Graph();
        Intent intent = getIntent();
        roomDestination = intent.getStringExtra("StopRoom");

        choixArriveeFragment = (ChoixArriveeFragment) getFragmentManager().findFragmentById(R.id.fragmentDestination);

        if (roomDestination!=null){
            String destinationText = "Vous souhaitez rejoindre l'accès " + roomDestination+ " du Cnam";
            Toast.makeText(this,destinationText,Toast.LENGTH_LONG).show();
            indexDestination = graph.findIndex(roomDestination)-1;
            choixArriveeFragment.setDestinationNumber(indexDestination);
            arriveeChoisie = true;
            arriveeText = roomDestination;
            setTrajetText();
            buttonTrajet.setText(findTrajetText);
        }
    }


    private void setTrajetText(){
        findTrajetText = "Chercher le trajet le plus court de " + departText + " à " + arriveeText;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
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

    /*public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            showDialog(LocationActivity.this, "No Scanner Found",
                    "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final ActionBarActivity act,
                                          CharSequence title, CharSequence message, CharSequence buttonYes,
                                          CharSequence buttonNo) {

        AlertDialog.Builder dowloadDialog = new AlertDialog.Builder(act);
        dowloadDialog
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonYes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Uri uri = Uri.parse("market://search?q=pname:"
                                        + "com.google.zxing.client.android");

                                Intent intent = new Intent(Intent.ACTION_VIEW,
                                        uri);
                                try {
                                    act.startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                }

                            }
                        })
                .setNegativeButton(buttonNo,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });

        return dowloadDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 0) {
            if(resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast.makeText(this,
                        "Content:" + contents + " Format:" + format,
                        Toast.LENGTH_LONG).show();
            }
        }
    }*/

    public void goToTrajet(View view) {
        Intent intent = new Intent(this, TrajetActivity.class);
        intent.putExtra("StartRoom",departText);
        intent.putExtra("StopRoom",arriveeText);
        intent.putExtra("DynamicPlan",true);
        startActivity(intent);
    }

    public void updateButtonTrajet(){
        setTrajetText();
        buttonTrajet.setText(findTrajetText);
        if (departChoisi && arriveeChoisie){
            buttonTrajet.setBackgroundResource(R.drawable.btn_degrade_blanc);
            buttonTrajet.setEnabled(true);
        }
    }

    @Override
    public void spinnerDepartSelect(String nomDepart) {
        departChoisi = true;
        departText = nomDepart;
        updateButtonTrajet();
    }

    @Override
    public void spinnerArriveeSelect(String nomArrivee) {
        arriveeChoisie = true;
        arriveeText = nomArrivee;
        updateButtonTrajet();
    }
}
