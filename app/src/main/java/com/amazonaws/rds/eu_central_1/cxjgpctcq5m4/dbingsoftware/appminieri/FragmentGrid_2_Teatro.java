package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class FragmentGrid_2_Teatro extends Fragment {


    MyListObject myObject;
    ArrayList<MyListObject> eventListTeatro;
    GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_grid_2, container, false);

        eventListTeatro = MainActivity.eventListTeatro;
        Log.i("messaggi", "FragmentGrid_2_Teatro : Ho copiato la lista dal MainActivity");

        gridView = (GridView) view.findViewById(R.id.customgrid);
        MyAdapter_Grid adapter=new MyAdapter_Grid(getActivity().getApplicationContext(), R.layout.list_events_grid,eventListTeatro);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                myObject= (MyListObject) gridView.getItemAtPosition(position);
                InfoEvento.item=myObject;
                Log.i("messaggi", "RES : " + gridView.getItemAtPosition(position));

                GoToInfoEvento();
            }
        });


        return view;
    }

    public void GoToInfoEvento() {
        Log.i("Messaggi", "Entrato in InfoEvento");
        Intent i = new Intent(getActivity().getApplicationContext(), InfoEvento.class);
        startActivity(i);
    }

}
