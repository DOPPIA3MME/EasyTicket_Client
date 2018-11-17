package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentListCarrello extends Fragment {

    private ListView lv;
    MyListObject myObject;
    List<MyListObject> eventList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view= inflater.inflate(R.layout.fragment_list_carrello, container, false);
        eventList= MainActivity.eventListCarrello;

    if(eventList!=null) {
        lv = (ListView) view.findViewById(R.id.list_carrello);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                myObject = (MyListObject) lv.getItemAtPosition(position);
                InfoEvento.item = myObject;
                GoToInfoEvento();
            }
        });


        MyAdapter_List_Carrello adapter = new MyAdapter_List_Carrello(getActivity().getApplicationContext(), R.layout.list_events_carrello, eventList);
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
