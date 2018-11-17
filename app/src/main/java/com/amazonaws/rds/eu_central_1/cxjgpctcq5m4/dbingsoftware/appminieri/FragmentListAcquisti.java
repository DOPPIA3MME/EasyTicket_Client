package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class FragmentListAcquisti extends Fragment {

    private ListView lv;
    MyListObject myObject;
    List<MyListObject> eventList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view= inflater.inflate(R.layout.fragment_list_acquisti, container, false);
        eventList= MainActivity.eventListAcquisti;

    if(eventList!=null) {
        lv = (ListView) view.findViewById(R.id.list_acquisti);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                myObject = (MyListObject) lv.getItemAtPosition(position);
                InfoEvento.item = myObject;
                GoToInfoEvento();
            }
        });


        MyAdapter_List_Acquisti adapter = new MyAdapter_List_Acquisti(getActivity().getApplicationContext(), R.layout.list_events_acquisti, eventList);
        lv.setAdapter(adapter);
    }

        return view;
    }

    public void GoToInfoEvento() {
        Log.i("Messaggi", "Entrato in InfoEvento da linear");
        Intent i = new Intent(getActivity().getApplicationContext(), InfoEvento.class);
        startActivity(i);
    }

}
