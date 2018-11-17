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


public class FragmentListSport extends Fragment {

    private ListView lv;
    MyListObject myObject;
    List<MyListObject> eventListSport;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view= inflater.inflate(R.layout.fragment_list, container, false);

        eventListSport= MainActivity.eventListSport;

        Log.i("messaggi", "FragmentList_Sport: Ho copiato la lista dalla MainActivity");

        lv = (ListView) view.findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long l) {

                myObject= (MyListObject) lv.getItemAtPosition(position);
                InfoEvento.item=myObject;
                GoToInfoEvento();
            }
        });

        MyAdapter_List2 adapter=new MyAdapter_List2(getActivity().getApplicationContext(), R.layout.list_events_linear2,eventListSport);
        lv.setAdapter(adapter);

        return view;
    }

    public void GoToInfoEvento() {
        Log.i("Messaggi", "Entrato in InfoEvento da linear");
        Intent i = new Intent(getActivity().getApplicationContext(), InfoEvento.class);
        startActivity(i);
    }
}
