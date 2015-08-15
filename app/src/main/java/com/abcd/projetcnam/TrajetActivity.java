package com.abcd.projetcnam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by julien on 11/08/2015.
 */
public class TrajetActivity extends Activity {
        String startRoom, stopRoom;
        Graph graph;
        int startIndex, stopIndex;
        MySurfaceView mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView));

        Intent intent = getIntent();
        if (intent != null){
            startRoom = intent.getStringExtra("StartRoom");
            stopRoom = intent.getStringExtra("StopRoom");
            stopRoom = intent.getStringExtra("blabla");
        }

        graph = new Graph();
        findRoomIndex();
        graph.findMinimumDistance(startIndex,stopIndex);
        for (int i :graph.getFinalPath()){

        }

    }

    public void findRoomIndex(){
        for (Node node :graph.getNodes()){
            if(node.getRoomName()==startRoom){
                startIndex = node.getIndex();
                break;
            }
        }
        for (Node node :graph.getNodes()) {
            if (node.getRoomName() == stopRoom) {
                stopIndex = node.getIndex();
                break;
            }
        }
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


}
