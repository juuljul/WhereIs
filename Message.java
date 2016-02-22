package com.abcd.projetcnam;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by julien on 01/05/2015.
 */
public class Message {

    public static void message (Context context, String s){
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

}
