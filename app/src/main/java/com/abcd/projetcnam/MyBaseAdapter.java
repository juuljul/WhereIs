package com.abcd.projetcnam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by julien on 25/08/2015.
 */
public class MyBaseAdapter extends BaseAdapter {

    ArrayList<SingleRow> list;
    DbHelperAdapter dbHelperAdapter;
    Context context;

    public MyBaseAdapter(Context c) {
        context = c;
        list = new ArrayList<SingleRow>();
        dbHelperAdapter = new DbHelperAdapter(c);
        dbHelperAdapter.updateArrays();
        for (int i=0;i<dbHelperAdapter.getAccessArray().size();i++){
            list.add(new SingleRow(dbHelperAdapter.getMatiereArray().get(i),
                    dbHelperAdapter.getHeureArray().get(i),dbHelperAdapter.getAccessArray().get(i)));
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row,parent,false);

        TextView matiereText = (TextView) row.findViewById(R.id.matiereText);
        TextView heureText = (TextView) row.findViewById(R.id.heureText);
        Button accessBtn = (Button) row.findViewById(R.id.accessBtn);

        final SingleRow temp = list.get(position);

        matiereText.setText(temp.matiere);
        heureText.setText(temp.hour);
        accessBtn.setText("ACCES  "+ temp.access);

        accessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrajetActivity.class);
                intent.putExtra("StartRoom","2");
                intent.putExtra("StopRoom",temp.access);
                context.startActivity(intent);
            }
        });
        return row;
    }
}
