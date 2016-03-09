package com.abcd.projetcnam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.otto.Subscribe;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChoixArriveeFragment extends Fragment {

    Spinner spinnerDestination;
    Button buttonValidate;
    ArrayAdapter arrayAdapter;
    OnArriveeSelected onArriveeSelected;
    Button buttonSchedule;
    Graph graph;

    TextView textDeTest;

    public ChoixArriveeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onArriveeSelected = (OnArriveeSelected) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_choix_arrivee,container,false);
        arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.salles,R.layout.spinner_layout);
        spinnerDestination = (Spinner) view.findViewById(R.id.spinnerDestination);
        spinnerDestination.setAdapter(arrayAdapter);

        buttonValidate = (Button) view.findViewById(R.id.buttonValidate);
        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onArriveeSelected.spinnerArriveeSelect(spinnerDestination.getSelectedItem().toString());
            }
        });

        buttonSchedule = (Button) view.findViewById(R.id.buttonSchedule);
        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                getActivity().startActivity(intent);
            }
        });

        textDeTest = (TextView) view.findViewById(R.id.textDeTest);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       // BusStation.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
       // BusStation.getBus().unregister(this);
    }

    /*@Subscribe
    public void recievedMessage(Message message){
        graph = new Graph();
        int indexDestination = graph.findIndex(message.getMsg())-1;
        spinnerDestination.setSelection(indexDestination);
        textDeTest.setText("L'acces de destination choisi a pour numero" + message.getMsg());
    }*/

    public void setDestinationNumber(int destinationNumber){
        spinnerDestination.setSelection(destinationNumber, true);
    }

    public interface OnArriveeSelected{
        public void spinnerArriveeSelect(String nomArrivee);
    }


}
