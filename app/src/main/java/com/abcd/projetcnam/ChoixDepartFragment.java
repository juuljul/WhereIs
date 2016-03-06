package com.abcd.projetcnam;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChoixDepartFragment extends Fragment {


    Spinner spinnerLocation;
    Button validateDepartButton, buttonScan;
    ArrayAdapter arrayAdapter;
    OnDepartSelected onDepartSelected;
    private final static int BAR_SCAN_CODE = 1;


    public ChoixDepartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onDepartSelected = (OnDepartSelected) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_choix_depart,container,false);
        spinnerLocation = (Spinner) view.findViewById(R.id.spinnerLocation);
        arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.salles,R.layout.spinner_layout);
        spinnerLocation.setAdapter(arrayAdapter);

        validateDepartButton = (Button) view.findViewById(R.id.validateDepartButton);
        validateDepartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDepartSelected.spinnerDepartSelect(spinnerLocation.getSelectedItem().toString());
            }
        });

        buttonScan = (Button) view.findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent("com.google.zxing.client.android.SCAN");
                PackageManager packageManager = getActivity().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(scanIntent, 0);
                if (activities.size() > 0) {
                    getActivity().startActivityForResult(scanIntent, BAR_SCAN_CODE);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BAR_SCAN_CODE){
            if (getActivity().RESULT_OK == resultCode){
                String code = data.getStringExtra("RESULT_CODE");
                Toast.makeText(getActivity(),"Le code est "+code, Toast.LENGTH_LONG).show();
            }
        }
    }



    public interface OnDepartSelected {
        public void spinnerDepartSelect(String nomDepart);
    }


}
