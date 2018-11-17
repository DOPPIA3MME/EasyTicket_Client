package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Accesso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class CarrelloMainActivity extends AppCompatActivity {


    public static boolean profiloEntered = false;
    Dialog myDialog;
    View parentLayout;
    Menu menu;
    private static String userCarrello="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/carrello";
    private static String userAcquisti="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/acquisti";


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    Toolbar toolbar;
    static Refresh g;
    private ProgressDialog pDialog;
    private String TAG = CarrelloMainActivity.class.getSimpleName();
    android.support.v4.app.FragmentTransaction transaction;


    public static int pagina;

    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Messaggi", "Sto in MainActivity");
        setContentView(R.layout.activity_main_carrello);


        parentLayout = findViewById(android.R.id.content);

        myDialog = new Dialog(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new FragmentCarrello(), "CARRELLO");
        tabAdapter.addFragment(new FragmentAcquisti(), "ACQUISTI");


        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        viewPager.setOffscreenPageLimit(2);


        if(MainActivity.eventListCarrello!=null) {
            Request request = new Request.Builder().url(userCarrello).build();
            Accesso.client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.i("messaggi", "FAILURE");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        //Log.i("messaggi", "response ACCETTATO : " + response);
                        String jsonData = response.body().string();
                        getCarrello(jsonData);
                    } else {
                        Log.i("messaggi", "response FALLITO : " + response);
                    }
                }
            });

        }



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("Messaggi", "PAGINA = " + tabLayout.getSelectedTabPosition());
                pagina = tabLayout.getSelectedTabPosition();
                switch (pagina) {
                    case 0:
                            Request request = new Request.Builder().url(userCarrello).build();
                            Accesso.client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                    Log.i("messaggi", "FAILURE");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if (response.isSuccessful()) {
                                       // Log.i("messaggi", "response ACCETTATO : " + response);
                                        String jsonData = response.body().string();
                                        getCarrello(jsonData);
                                    } else {
                                        Log.i("messaggi", "response FALLITO : " + response);
                                    }
                                }
                            });
                    case 1:

                     /*   Request request2 = new Request.Builder().url(userAcquisti).build();
                        Accesso.client.newCall(request2).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                                Log.i("messaggi", "FAILURE");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()) {
                                   // Log.i("messaggi", "response ACCETTATO : " + response);
                                    String jsonData = response.body().string();
                                    getAcquisti(jsonData);
                                } else {
                                    Log.i("messaggi", "response FALLITO : " + response);
                                }
                            }
                        });*/





                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });



        Request request2 = new Request.Builder().url(userAcquisti).build();
        Accesso.client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("messaggi", "FAILURE");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Log.i("messaggi", "response ACCETTATO : " + response);
                    String jsonData = response.body().string();
                    getAcquisti(jsonData);
                } else {
                    Log.i("messaggi", "response FALLITO : " + response);
                }
            }
        });



    }




    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            String recipeId = appLinkData.getLastPathSegment();
            Uri appData = Uri.parse("content://com.elasticbeanstalk.eu-central-1.ingsoftw/").buildUpon()
                    .appendPath(recipeId).build();
             //  showEvent(appData);
        }
    }





    //Gestisce la topbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inserisce il menu con i tasti refresh,ordina e changeview
       // getMenuInflater().inflate(R.menu.toolbar_home, menu);

        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu=menu;
        return super.onPrepareOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();




        return super.onOptionsItemSelected(item);
    }


    public void getCarrello(String jsonData){

        MainActivity.eventListCarrello.clear();
        MainActivity.eventListCarrelloId.clear();
        FragmentCarrello.totale=0;

        if (jsonData != null) {
            try {

                JSONArray events = new JSONArray(jsonData);
                for (int i = 0; i < events.length(); i++) {

                    JSONObject c = events.getJSONObject(i);
                    //prendo tutte le info dell'evento
                    String id = c.getString("id");
                    String nome = c.getString("nome");
                    String localita = c.getString("localita");
                    String data = c.getString("data");
                    String costo_s = c.getString("costo");
                    float costo = Float.parseFloat(costo_s);
                    String tipologia = c.getString("tipologia");
                    String image = c.getString("img");
                    String indirizzo = c.getString("indirizzo");
                    String descrizione = c.getString("descrizione");
                    String sotto_tipologia = c.getString("sotto_tipologia");

                    MyListObject item = new MyListObject();
                    item.setName(nome);
                    item.setLocalita(localita);
                    item.setData(data);
                    item.setCosto(costo);
                    item.setId(id);
                    item.setTipologia(tipologia);
                    item.setImage(image);
                    item.setIndirizzo(indirizzo);
                    item.setDescrizione(descrizione);
                    item.setSotto_tipologia(sotto_tipologia);
                    MainActivity.eventListCarrello.add(item);
                    MainActivity.eventListCarrelloId.add(item.getId());
                    FragmentCarrello.totale=  FragmentCarrello.totale+item.getCosto();
                }

                Log.i("messaggi", "ListaCarrello creata dopo aver scorso tutti gli eventi");
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

        } else {
            Log.e(TAG, "Errore.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Impossibile caricare gli eventi. Controlla la tua connessione internet",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }

        g = new Refresh();
        g.execute();
    }



    public void getAcquisti(String jsonData){


        MainActivity.eventListAcquisti.clear();
        MainActivity.eventListAcquistiId.clear();

        if (jsonData != null) {
            try {

                JSONArray events = new JSONArray(jsonData);
                for (int i = 0; i < events.length(); i++) {

                    JSONObject c = events.getJSONObject(i);
                    //prendo tutte le info dell'evento
                    String id = c.getString("evento_id");
                    String nome = c.getString("nome_evento");
                    String localita = c.getString("localita");
                    String data = c.getString("data");
                    String costo_s = c.getString("costo");
                    float costo = Float.parseFloat(costo_s);
                    String tipologia = c.getString("tipologia");
                    String image = c.getString("img");
                    String indirizzo = c.getString("indirizzo");
                     String descrizione = c.getString("descrizione");
                    String sotto_tipologia = c.getString("sotto_tipologia");
                    String acquisto_code = c.getString("acquisto_code");
                    String user_id = c.getString("user_id");

                    MyListObject item = new MyListObject();
                    item.setName(nome);
                    item.setLocalita(localita);
                    item.setData(data);
                    item.setCosto(costo);
                    item.setId(id);
                    item.setTipologia(tipologia);
                    item.setImage(image);
                    item.setIndirizzo(indirizzo);
                    item.setDescrizione(descrizione);
                    item.setSotto_tipologia(sotto_tipologia);
                    item.setAcquisto_code(acquisto_code);
                    item.setUser_id(user_id);
                    MainActivity.eventListAcquisti.add(item);
                    MainActivity.eventListAcquistiId.add(item.getId());
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

        } else {
            Log.e(TAG, "Errore.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Impossibile caricare gli eventi. Controlla la tua connessione internet",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }


        g = new Refresh();
        g.execute();

    }



    public class Refresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            FragmentListCarrello fragment = new FragmentListCarrello();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_carrello, fragment).commitAllowingStateLoss();
            FragmentCarrello.textTotale.setText("TOTALE : "+FragmentCarrello.totale+" €");



            FragmentListAcquisti fragment2 = new FragmentListAcquisti();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_acquisti, fragment2).commitAllowingStateLoss();
            FragmentAcquisti.textTotale.setText("Biglietti eventi acquistati : "+MainActivity.eventListAcquisti.size());
        }
    }



}
