package com.abcd.projetcnam;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class LocationActivity extends ActionBarActivity {

    String roomDestination;
    int indexDestination;
    Spinner spinnerDestination, spinnerLocation;
    ArrayAdapter arrayAdapter;
    Graph graph;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        spinnerDestination = (Spinner) findViewById(R.id.spinnerDestination);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        arrayAdapter= ArrayAdapter.createFromResource(this,R.array.salles, R.layout.spinner_layout);
        spinnerDestination.setAdapter(arrayAdapter);
        spinnerLocation.setAdapter(arrayAdapter);

        graph = new Graph();
        Intent intent = getIntent();
        roomDestination = intent.getStringExtra("StopRoom");

        if (roomDestination!=null){
            String destinationText = "Vous souhaitez rejoindre l'accès " + roomDestination+ " du Cnam";
            Toast.makeText(this,destinationText,Toast.LENGTH_LONG).show();
            indexDestination = graph.findIndex(roomDestination)-1;
            spinnerDestination.setSelection(indexDestination);
        }
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

    public void scanQR(View v) {
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
    }

    public void goToTrajet(View view) {
        Intent intent = new Intent(this, TrajetActivity.class);
        intent.putExtra("StartRoom",spinnerLocation.getSelectedItem().toString());
        intent.putExtra("StopRoom",spinnerDestination.getSelectedItem().toString());
        intent.putExtra("DynamicPlan",true);
        startActivity(intent);
    }

    public void goToSchedule(View view) {
        Intent intent = new Intent(this,ScheduleActivity.class);
        startActivity(intent);
    }
}
