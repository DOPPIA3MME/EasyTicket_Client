package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Accesso;

public class FragmentCinema extends Fragment {

    public enum Mode {
        AZIONE,
        ROMANTICO,
        COMICO,
    }

    public static Mode mode= Mode.AZIONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_cinema, container, false);
        Log.i("messaggi","FragmentCinema: Creato");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.profiloEntered==true)
                    GoToCarrello();
                else
                    GoToAccesso();
            }
        });

        return view;
    }


    public void GoToCarrello() {
        Log.i("Messaggi", "Entrato in Carrello");
        Intent i = new Intent(getContext(), CarrelloMainActivity.class);
        startActivity(i);
    }

    public void GoToAccesso() {
        Intent i = new Intent(getContext(), Accesso.class);
        startActivity(i);
    }
}
