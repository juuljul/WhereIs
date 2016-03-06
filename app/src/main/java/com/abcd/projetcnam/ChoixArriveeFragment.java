package com.abcd.projetcnam;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChoixArriveeFragment extends Fragment {

    Spinner spinnerDestination;
    Button buttonValidate;
    ArrayAdapter arrayAdapter;
    OnArriveeSelected onArriveeSelected;
    Button buttonSchedule;

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


        return view;
    }

    public void setDestinationNumber(int destinationNumber){
        spinnerDestination.setSelection(destinationNumber,true);
    }

    public interface OnArriveeSelected{
        public void spinnerArriveeSelect(String nomArrivee);
    }


}
