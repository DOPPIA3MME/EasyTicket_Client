package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Accesso;


public class Home extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Messaggi", "Sto in Home");
        view= inflater.inflate(R.layout.activity_home, container, false);

        //definisco il bottone presente nella home
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Apro l'activity del carrello", Snackbar.LENGTH_LONG).setAction("Carrello", null).show();
                if(MainActivity.profiloEntered==true)
                    GoToCarrelloMain();
                else
                    GoToAccesso();

            }
        });

        return view;
    }


    public void GoToCarrelloMain() {
        Intent i = new Intent(getContext(), CarrelloMainActivity.class);
        startActivity(i);
    }

    public void GoToAccesso() {
        Intent i = new Intent(getContext(), Accesso.class);
        startActivity(i);
    }

}

