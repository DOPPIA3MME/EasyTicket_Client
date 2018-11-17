package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class FragmentGrid_Tipologie extends Fragment {

    MyListObject myObject;
    ArrayList<MyListObject> eventListConcerto;
    ArrayList<MyListObject> eventListFiera;
    ArrayList<MyListObject> eventListCinema;
    ArrayList<MyListObject> eventList;
    ArrayList<MyListObject> eventListSport;
    ArrayList<MyListObject> eventListTeatro;
    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_grid_1, container, false);

        eventList=MainActivity.eventList;
        eventListConcerto = MainActivity.eventListConcerto;
        eventListFiera = MainActivity.eventListFiera;
        eventListCinema = MainActivity.eventListCinema;
        eventListSport = MainActivity.eventListSport;
        eventListTeatro = MainActivity.eventListTeatro;


        gridView = (GridView) view.findViewById(R.id.customgrid);
        MyAdapter_Grid adapter1=new MyAdapter_Grid(getActivity().getApplicationContext(), R.layout.list_events_grid,eventListFiera);
        MyAdapter_Grid adapter2=new MyAdapter_Grid(getActivity().getApplicationContext(), R.layout.list_events_grid,eventListConcerto);
        MyAdapter_Grid adapter3=new MyAdapter_Grid(getActivity().getApplicationContext(), R.layout.list_events_grid,eventListCinema);
        MyAdapter_Grid adapter4=new MyAdapter_Grid(getActivity().getApplicationContext(), R.layout.list_events_grid,eventList);
        MyAdapter_Grid adapter5=new MyAdapter_Grid(getActivity().getApplicationContext(), R.layout.list_events_grid,eventListSport);
        MyAdapter_Grid adapter6=new MyAdapter_Grid(getActivity().getApplicationContext(), R.layout.list_events_grid,eventListTeatro);
        gridView.setAdapter(adapter1);
        gridView.setAdapter(adapter2);
        gridView.setAdapter(adapter3);
        gridView.setAdapter(adapter4);
        gridView.setAdapter(adapter5);
        gridView.setAdapter(adapter6);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long l) {
                myObject= (MyListObject) gridView.getItemAtPosition(position);
                InfoEvento.item=myObject;
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
