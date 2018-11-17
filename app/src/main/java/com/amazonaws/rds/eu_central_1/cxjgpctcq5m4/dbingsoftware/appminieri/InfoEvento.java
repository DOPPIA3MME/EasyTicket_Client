package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Accesso;
import com.facebook.CallbackManager;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class InfoEvento extends AppCompatActivity {

    public static MyListObject item;
    Snackbar snackbar;
    TextView tv;
    private CallbackManager callbackManager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_evento);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        TextView data = (TextView) toolbar.findViewById(R.id.toolbar_data);
        TextView tipologia = (TextView) toolbar.findViewById(R.id.toolbar_tipologia);
        TextView localita = (TextView) toolbar.findViewById(R.id.toolbar_localita);
        TextView prezzo = (TextView) toolbar.findViewById(R.id.toolbar_prezzo);
        TextView indirizzo = (TextView) toolbar2.findViewById(R.id.toolbar2_indirizzo);
        TextView descrizione = (TextView) findViewById(R.id.tDescrizione);
        ImageView imageview= (ImageView) findViewById(R.id.image_event2);
        Picasso.get().load(item.getImage()).placeholder(R.drawable.logo).error(R.drawable.notfound).into(imageview);


        title.setText(item.getName());
        data.setText(item.getData());
        tipologia.setText(item.getTipologia()+" - "+item.getSotto_tipologia());
        localita.setText(item.getLocalita().toUpperCase());
        prezzo.setText(item.getCosto()+"€");
        indirizzo.setText(item.getIndirizzo());
        descrizione.setText(item.getDescrizione());


        toolbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+item.getIndirizzo());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null)
                startActivity(mapIntent);

            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_Putshopping);
        if(MainActivity.profiloEntered==false)
            fab.setVisibility(View.INVISIBLE);


      /* if(MainActivity.eventListCarrelloId.contains(item.getId()) || MainActivity.eventListAcquistiId.contains(item.getId())) {
           fab.setImageResource(R.drawable.check);
       }*/

        fab.setOnClickListener(new View.OnClickListener() {
              @RequiresApi(api = Build.VERSION_CODES.M)
              @Override
              public void onClick(View view) {
                // if(!MainActivity.eventListCarrelloId.contains(item.getId())&&!MainActivity.eventListAcquistiId.contains(item.getId())){
                     fab.setImageResource(R.drawable.check);
                     SnackBar("Aggiunto nel carrello");

                     String addCarrelloRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/carrello/add/item/"+item.getId();

                     Request request=new Request.Builder().url(addCarrelloRequest).build();
                     Accesso.client.newCall(request).enqueue(new Callback() {
                         @Override
                         public void onFailure(Call call, IOException e) {
                             e.printStackTrace();
                             Log.i("messaggi","FAILURE");
                         }

                         @Override
                         public void onResponse(Call call, Response response) throws IOException {
                             if(response.isSuccessful()) {
                                 Log.i("messaggi", "response put in shop ACCETTATO : " + response);
                                 MainActivity.eventListCarrello.add(item);
                                 MainActivity.eventListCarrelloId.add(item.getId());
                                 FragmentCarrello.totale=FragmentCarrello.totale+item.getCosto();
                             }
                             else {
                                 Log.i("messaggi", "response put in shop FALLITO : " + response);
                             }
                         }
                     });

               //  }
              }
          });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void SnackBar(String text){
        CoordinatorLayout clayout=null;
        clayout = (CoordinatorLayout) findViewById(R.id.clayout_infoevento);
        snackbar = Snackbar.make(clayout, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getColor(R.color.colorPrimaryDark));
        tv =  snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        // tv.setTextSize(16);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
