package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Accesso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentCarrello extends Fragment {


    public static float totale;
    public static TextView textTotale;
    public static boolean acquistaTutto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_carrello, container, false);
        Log.i("messaggi","FragmentCarrello: Creato");

        textTotale=(TextView) view.findViewById(R.id.totale_carrello);



        Button b=view.findViewById(R.id.buttonAcquistaTutto);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.eventListCarrello.size()==0)
                    Toast.makeText(getApplicationContext(), "Carrello VUOTO!", Toast.LENGTH_LONG).show();
                else {
                    acquistaTutto=true;
                    GoToPagamento();
                }

            }
        });


        return view;
    }



    public void GoToPagamento() {
        Intent i = new Intent(getContext(), Pagamento.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
