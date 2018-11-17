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
import android.widget.Toast;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentListCinema extends Fragment {

    private ListView lv;
    MyListObject myObject;
    List<MyListObject> eventListCinema;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view= inflater.inflate(R.layout.fragment_list, container, false);
        eventListCinema= MainActivity.eventListCinema;

        lv = (ListView) view.findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long l) {

                myObject= (MyListObject) lv.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                InfoEvento.item=myObject;
                GoToInfoEvento();
            }
        });

        MyAdapter_List2 adapter=new MyAdapter_List2(getActivity().getApplicationContext(), R.layout.list_events_linear2,eventListCinema);
        lv.setAdapter(adapter);

        return view;
    }

    public void GoToInfoEvento() {
        Log.i("Messaggi", "Entrato in InfoEvento da linear");
        Intent i = new Intent(getActivity().getApplicationContext(), InfoEvento.class);
        startActivity(i);
    }
}
