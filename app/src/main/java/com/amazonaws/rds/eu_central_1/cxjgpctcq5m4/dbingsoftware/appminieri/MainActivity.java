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


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public enum Mode {
        LIST,
        IMAGES,
        GRID,
    }

    public enum Order {
        NAME,
        DATE,
        PREZZO,
    }

    private String TAG = MainActivity.class.getSimpleName();
    private int eventiTotali=0;
    // URL to get events JSON
    private static String url = "http://ingsoftw.eu-central-1.elasticbeanstalk.com/get/eventi";
    // URL to get user info JSON
    private static String userRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/userinfo";


    public static boolean profiloEntered = false;
    Dialog myDialog;
    View parentLayout;
    Menu menu;
    Menu menu_items;
    MenuItem nav_categorie;
    MenuItem nav_cercaevento;
    MenuItem nav_share;
    MenuItem nav_profilo;
    MenuItem nav_login;
    MenuItem nav_info;
    MenuItem nav_impostazioni;
    NavigationView navigationView;
    View headerView;
    TextView nav_title;
    TextView nav_mail;
    public static Mode mode= Mode.IMAGES;
    public static Order order=Order.NAME;
    private ProgressDialog pDialog;
    GetEvents g;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    Toolbar toolbar;


    public static ArrayList<MyListObject> eventList_begin;
    public static ArrayList<MyListObject> eventList;
    ArrayList<MyListObject> eventList2;

    public static ArrayList<MyListObject> eventListCinema;
    public static ArrayList<MyListObject> eventListConcerto;
    public static ArrayList<MyListObject> eventListFiera;
    public static ArrayList<MyListObject> eventListSport;
    public static ArrayList<MyListObject> eventListTeatro;
    public static ArrayList<MyListObject> eventListCarrello;
    public static ArrayList<String> eventListCarrelloId;
    public static ArrayList<MyListObject> eventListAcquisti;
    public static ArrayList<String> eventListAcquistiId;

    public static int pagina;

    Snackbar snackbar;
    TextView tv;
    android.support.v4.app.FragmentTransaction transaction;

    public static String userNome;
    public static String userCognome;
    public static String userUsername;
    public static Uri userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Messaggi", "Sto in MainActivity");
        setContentView(R.layout.activity_main);


        parentLayout = findViewById(android.R.id.content);
        eventList_begin = new ArrayList();
        eventList2 = new ArrayList();
        eventList = new ArrayList();
        eventListCinema = new ArrayList();
        eventListConcerto = new ArrayList();
        eventListFiera = new ArrayList();
        eventListSport = new ArrayList();
        eventListTeatro = new ArrayList();
        eventListCarrello = new ArrayList();
        eventListCarrelloId = new ArrayList();
        eventListAcquistiId = new ArrayList();
        eventListAcquisti = new ArrayList();


        g = new GetEvents();
        g.execute();



        myDialog = new Dialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new Home(), "HOME");
        tabAdapter.addFragment(new FragmentCinema(), "CINEMA");
        tabAdapter.addFragment(new FragmentConcerto(), "CONCERTO");
        tabAdapter.addFragment(new FragmentFiera(), "FIERA");
        tabAdapter.addFragment(new FragmentSport(), "SPORT");
        tabAdapter.addFragment(new FragmentTeatro(), "TEATRO");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        viewPager.setOffscreenPageLimit(6);  //carica tutte le 6 pages presenti nel tab


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("Messaggi", "PAGINA = " + tabLayout.getSelectedTabPosition());
                pagina = tabLayout.getSelectedTabPosition();
                switch (pagina) {
                    case 0:
                        menu.findItem(R.id.sottotipo).setVisible(false);
                        break;
                    case 1:
                        menu.findItem(R.id.sottotipo).setVisible(true);
                        if (FragmentCinema.mode == FragmentCinema.Mode.AZIONE)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.azione);
                        else if (FragmentCinema.mode == FragmentCinema.Mode.COMICO)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.comico);
                        else
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.romantico);
                        break;
                    case 2:
                        menu.findItem(R.id.sottotipo).setVisible(true);
                        if (FragmentConcerto.mode == FragmentConcerto.Mode.SOLISTA)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.microfono2_white);
                        else if (FragmentConcerto.mode == FragmentConcerto.Mode.DJ)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.dj_white);
                        else
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.orchestra_white);
                        break;
                    case 3:
                        menu.findItem(R.id.sottotipo).setVisible(true);
                        if (FragmentFiera.mode == FragmentFiera.Mode.GAMING)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.gaming);
                        else if (FragmentFiera.mode == FragmentFiera.Mode.CIBO)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.cibo);
                        else
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.cultura);
                        break;
                    case 4:
                        menu.findItem(R.id.sottotipo).setVisible(true);
                        if (FragmentSport.mode == FragmentSport.Mode.CALCIO)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.calcio);
                        else if (FragmentSport.mode == FragmentSport.Mode.BASKET)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.basket);
                        else if(FragmentSport.mode == FragmentSport.Mode.TENNIS)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.tennis);
                        else if(FragmentSport.mode == FragmentSport.Mode.FOOTBALL)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.football);
                        else if(FragmentSport.mode == FragmentSport.Mode.LOTTA)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.lotta);
                        break;
                    case 5:
                        menu.findItem(R.id.sottotipo).setVisible(true);
                        if (FragmentTeatro.mode == FragmentTeatro.Mode.DRAMMATICO)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.drammatico);
                        else if (FragmentTeatro.mode == FragmentTeatro.Mode.COMMEDIA)
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.comico);
                        else
                            menu.findItem(R.id.sottotipo).setIcon(R.drawable.opera);
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





        //Definisco il tasto sulla toolbar per aprire la navigation view
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        initNavigationView();


        Log.i("Messaggi", "profilo entered prima dei controlli = " + profiloEntered);
        if (profiloEntered == true) {
            nav_login.setVisible(false);
            nav_profilo.setVisible(true);
            FragmentCarrello.totale=0;

            //RICHIESTA GET info user
            final Request request=new Request.Builder().url(userRequest).build();
            Accesso.client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    Log.i("messaggi","FAILURE");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()) {
                       // Log.i("messaggi", "response ACCETTATO : " + response);
                        String jsonData = response.body().string();
                        getUser(jsonData);
                    }
                    else {
                        Log.i("messaggi", "response FALLITO : " + response);
                    }
                }
            });


            String userCarrello="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/carrello";
            String userAcquisti="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/acquisti";
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

            Request request3 = new Request.Builder().url(userCarrello).build();
            Accesso.client.newCall(request3).enqueue(new Callback() {
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


        } else {
            nav_login.setVisible(true);
            nav_profilo.setVisible(false);
        }


        //Collegate with web site
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        handleIntent(getIntent());

    }


    public void getAcquisti(String jsonData){
        if (jsonData != null) {
            try {

                JSONArray events = new JSONArray(jsonData);
                for (int i = 0; i < events.length(); i++) {
                    JSONObject c = events.getJSONObject(i);
                    String id = c.getString("evento_id");
                    MyListObject item = new MyListObject();
                    item.setId(id);
                    eventListAcquistiId.add(item.getId());
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
    }

    public void getCarrello(String jsonData){

        if (jsonData != null) {
            try {

                JSONArray events = new JSONArray(jsonData);
                for (int i = 0; i < events.length(); i++) {
                    JSONObject c = events.getJSONObject(i);
                    String id = c.getString("id");
                    MyListObject item = new MyListObject();
                    item.setId(id);
                    eventListCarrelloId.add(item.getId());
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
    }


    public void initNavigationView(){
        //prendo la navigation view(il menu a tendina)
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //prendo il menu degli items nella navigation view (categorie,aggiungi evento...login)
        menu_items = navigationView.getMenu();
        //e tutti gli items
        nav_categorie = menu_items.findItem(R.id.nav_categorie);
        nav_cercaevento= menu_items.findItem(R.id.nav_cercaevento);
        nav_share=menu_items.findItem(R.id.nav_share);
        nav_profilo = menu_items.findItem(R.id.profile);
        nav_login = menu_items.findItem(R.id.login);
        nav_info=menu_items.findItem(R.id.info);
        nav_impostazioni=menu_items.findItem(R.id.impostazioni);


        //prendo l'header della navigation view
        headerView = navigationView.getHeaderView(0);
        nav_title = (TextView) headerView.findViewById(R.id.nav_header_title);
        nav_mail = (TextView) headerView.findViewById(R.id.nav_header_mail);



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
        getMenuInflater().inflate(R.menu.toolbar_home, menu);

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
        int id = item.getItemId();


       if(id== R.id.orderby && order== Order.NAME){
           item.setIcon(R.drawable.order_by_date);
           order=Order.DATE;
           OrderByName(eventList);
           OrderByName(eventListCinema);
           OrderByName(eventListConcerto);
           OrderByName(eventListFiera);
           OrderByName(eventListSport);
           OrderByName(eventListTeatro);
           Void v1=null;
           g.onPostExecute(v1);
           if(pagina==0)
              SnackBar("Home","Lista ordinata in base al nome");
           else if(pagina==1)
               SnackBar("Cinema","Lista ordinata in base al nome");
           else if(pagina==2)
               SnackBar("Concerto","Lista ordinata in base al nome");
           else if(pagina==3)
               SnackBar("Fiera","Lista ordinata in base al nome");
           else if(pagina==4)
               SnackBar("Sport","Lista ordinata in base al nome");
           else if(pagina==5)
               SnackBar("Teatro","Lista ordinata in base al nome");
       }
       else if(id== R.id.orderby && order== Order.DATE){
           item.setIcon(R.drawable.order_by_prezzo);
           order=Order.PREZZO;
           OrderByData(eventList);
           OrderByData(eventListCinema);
           OrderByData(eventListConcerto);
           OrderByData(eventListFiera);
           OrderByData(eventListSport);
           OrderByData(eventListTeatro);
           Void v1=null;
           g.onPostExecute(v1);
           if(pagina==0)
               SnackBar("Home","Lista ordinata in base alla data");
           else if(pagina==1)
               SnackBar("Cinema","Lista ordinata in base alla data");
           else if(pagina==2)
               SnackBar("Concerto","Lista ordinata in base alla data");
           else if(pagina==3)
               SnackBar("Fiera","Lista ordinata in base alla data");
           else if(pagina==4)
               SnackBar("Sport","Lista ordinata in base alla data");
           else if(pagina==5)
               SnackBar("Teatro","Lista ordinata in base alla data");
       }
       else if(id== R.id.orderby && order== Order.PREZZO){
           item.setIcon(R.drawable.order_by_name);
           order=Order.NAME;
           OrderByPrezzo(eventList);
           OrderByPrezzo(eventListCinema);
           OrderByPrezzo(eventListConcerto);
           OrderByPrezzo(eventListFiera);
           OrderByPrezzo(eventListSport);
           OrderByPrezzo(eventListTeatro);
           Void v1=null;
           g.onPostExecute(v1);
           if(pagina==0)
               SnackBar("Home","Lista ordinata in base al prezzo");
           else if(pagina==1)
               SnackBar("Cinema","Lista ordinata in base al prezzo");
           else if(pagina==2)
               SnackBar("Concerto","Lista ordinata in base al prezzo");
           else if(pagina==3)
               SnackBar("Fiera","Lista ordinata in base al prezzo");
           else if(pagina==4)
               SnackBar("Sport","Lista ordinata in base al prezzo");
           else if(pagina==5)
               SnackBar("Teatro","Lista ordinata in base al prezzo");
       }
        else if(id== R.id.refresh){
           //order=Order.NAME;
           eventList.clear();
           eventListCinema.clear();
           eventListConcerto.clear();
           eventListFiera.clear();
           eventListSport.clear();
           eventListTeatro.clear();
            g=new GetEvents();
            g.execute();
           Toast.makeText(getApplicationContext(), "Eventi aggiornati", Toast.LENGTH_LONG).show();
        }
        else if(id== R.id.changeview && mode== Mode.LIST){
            item.setIcon(R.drawable.eventsmultipleview_white);
            mode= Mode.IMAGES;
            Void v=null;
            g.onPostExecute(v);
        }
        else if(id== R.id.changeview && mode== Mode.IMAGES){
            item.setIcon(R.drawable.eventslistview_white);
            mode= Mode.GRID;
            Void v=null;
            g.onPostExecute(v);
        }
        else if(id== R.id.changeview && mode== Mode.GRID){
            item.setIcon(R.drawable.eventslinearview_white);
            mode= Mode.LIST;
            Void v=null;
            g.onPostExecute(v);
        }


        if(pagina==1){
            if (id == R.id.sottotipo && FragmentCinema.mode == FragmentCinema.Mode.AZIONE) {
                item.setIcon(R.drawable.comico);
                FragmentCinema.mode = FragmentCinema.Mode.COMICO;
                eventListCinema.clear();
                GetCinemaEvents("Azione");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Cinema", "AZIONE");
            }
            else if (id == R.id.sottotipo && FragmentCinema.mode == FragmentCinema.Mode.COMICO) {
                item.setIcon(R.drawable.romantico);
                FragmentCinema.mode = FragmentCinema.Mode.ROMANTICO;
                eventListCinema.clear();
                GetCinemaEvents("Comico");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Cinema","COMICO");
            } else if (id == R.id.sottotipo && FragmentCinema.mode == FragmentCinema.Mode.ROMANTICO) {
                item.setIcon(R.drawable.azione);
                FragmentCinema.mode = FragmentCinema.Mode.AZIONE;
                eventListCinema.clear();
                GetCinemaEvents("Romantico");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Cinema","ROMANTICO");
            }
        }
        else if(pagina==2) {
            if (id == R.id.sottotipo && FragmentConcerto.mode == FragmentConcerto.Mode.SOLISTA) {
                item.setIcon(R.drawable.dj_white);
                FragmentConcerto.mode = FragmentConcerto.Mode.DJ;
                eventListConcerto.clear();
                GetConcertoEvents("Solista");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Concerto","SOLISTA");
            } else if (id == R.id.sottotipo && FragmentConcerto.mode == FragmentConcerto.Mode.DJ) {
                item.setIcon(R.drawable.orchestra_white);
                FragmentConcerto.mode = FragmentConcerto.Mode.ORCHESTRA;
                eventListConcerto.clear();
                GetConcertoEvents("Dj Set");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Concerto","DJ SET");
            } else if (id == R.id.sottotipo && FragmentConcerto.mode == FragmentConcerto.Mode.ORCHESTRA) {
                item.setIcon(R.drawable.microfono2_white);
                FragmentConcerto.mode = FragmentConcerto.Mode.SOLISTA;
                eventListConcerto.clear();
                GetConcertoEvents("Orchestra");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Concerto","ORCHESTRA");
            }
        }
        else if(pagina==3){
            if (id == R.id.sottotipo && FragmentFiera.mode == FragmentFiera.Mode.GAMING) {
                item.setIcon(R.drawable.cibo);
                FragmentFiera.mode = FragmentFiera.Mode.CIBO;
                eventListFiera.clear();
                GetFieraEvents("Gaming");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Fiera","GAMING");
            } else if (id == R.id.sottotipo && FragmentFiera.mode == FragmentFiera.Mode.CIBO) {
                item.setIcon(R.drawable.cultura);
                FragmentFiera.mode = FragmentFiera.Mode.CULTURA;
                eventListFiera.clear();
                GetFieraEvents("Cibo");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Fiera","CIBO");
            } else if (id == R.id.sottotipo && FragmentFiera.mode == FragmentFiera.Mode.CULTURA) {
                item.setIcon(R.drawable.gaming);
                FragmentFiera.mode = FragmentFiera.Mode.GAMING;
                eventListFiera.clear();
                GetFieraEvents("Cultura");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Fiera","CULTURA");
            }
        }
        else if(pagina==4){
            if (id == R.id.sottotipo && FragmentSport.mode == FragmentSport.Mode.CALCIO) {
                item.setIcon(R.drawable.basket);
                FragmentSport.mode = FragmentSport.Mode.BASKET;
                eventListSport.clear();
                GetSportEvents("Calcio");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Sport","CALCIO");
            } else if (id == R.id.sottotipo && FragmentSport.mode == FragmentSport.Mode.BASKET) {
                item.setIcon(R.drawable.tennis);
                FragmentSport.mode = FragmentSport.Mode.TENNIS;
                eventListSport.clear();
                GetSportEvents("Basket");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Sport","BASKET");
            } else if (id == R.id.sottotipo && FragmentSport.mode == FragmentSport.Mode.TENNIS) {
                item.setIcon(R.drawable.football);
                FragmentSport.mode = FragmentSport.Mode.FOOTBALL;
                eventListSport.clear();
                GetSportEvents("Tennis");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Sport","TENNIS");
            }
            else if (id == R.id.sottotipo && FragmentSport.mode == FragmentSport.Mode.FOOTBALL) {
                item.setIcon(R.drawable.lotta);
                FragmentSport.mode = FragmentSport.Mode.LOTTA;
                eventListSport.clear();
                GetSportEvents("Football");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Sport","FOOTBALL");
            }
            else if (id == R.id.sottotipo && FragmentSport.mode == FragmentSport.Mode.LOTTA) {
                item.setIcon(R.drawable.baseball);
                FragmentSport.mode = FragmentSport.Mode.BASEBALL;
                eventListSport.clear();
                GetSportEvents("Lotta");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Sport","LOTTA");
            }
            else if (id == R.id.sottotipo && FragmentSport.mode == FragmentSport.Mode.BASEBALL) {
                item.setIcon(R.drawable.calcio);
                FragmentSport.mode = FragmentSport.Mode.CALCIO;
                eventListSport.clear();
                GetSportEvents("Baseball");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Sport","BASEBALL");
            }
        }
        else if(pagina==5){
            if (id == R.id.sottotipo && FragmentTeatro.mode == FragmentTeatro.Mode.DRAMMATICO) {
                item.setIcon(R.drawable.comico);
                FragmentTeatro.mode = FragmentTeatro.Mode.COMMEDIA;
                eventListTeatro.clear();
                GetTeatroEvents("Drammatico");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Teatro","DRAMMATICO");
            } else if (id == R.id.sottotipo && FragmentTeatro.mode == FragmentTeatro.Mode.COMMEDIA) {
                item.setIcon(R.drawable.opera);
                FragmentTeatro.mode = FragmentTeatro.Mode.OPERA;
                eventListTeatro.clear();
                GetTeatroEvents("Commedia");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Teatro","COMMEDIA");
            } else if (id == R.id.sottotipo && FragmentTeatro.mode == FragmentTeatro.Mode.OPERA) {
                item.setIcon(R.drawable.drammatico);
                FragmentTeatro.mode = FragmentTeatro.Mode.DRAMMATICO;
                eventListTeatro.clear();
                GetTeatroEvents("Opera");
                Void v = null;
                g.onPostExecute(v);
                SnackBar("Teatro","OPERA");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void GetSportEvents(String tipo){
        for(MyListObject o: eventList_begin){
            if(o.getSotto_tipologia().compareTo(tipo)==0)
                eventListSport.add(o);
        }
    }

    public void GetFieraEvents(String tipo){
        for(MyListObject o: eventList_begin){
            if(o.getSotto_tipologia().compareTo(tipo)==0)
                eventListFiera.add(o);
        }
    }

    public void GetConcertoEvents(String tipo){
        for(MyListObject o: eventList_begin){
            if(o.getSotto_tipologia().compareTo(tipo)==0)
                eventListConcerto.add(o);
        }
    }

    public void GetCinemaEvents(String tipo){
        for(MyListObject o: eventList_begin){
            if(o.getSotto_tipologia().compareTo(tipo)==0)
                eventListCinema.add(o);
        }
    }

    public void GetTeatroEvents(String tipo){
        for(MyListObject o: eventList_begin){
            if(o.getSotto_tipologia().compareTo(tipo)==0)
                eventListTeatro.add(o);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void SnackBar(String tipologia,String text){
        CoordinatorLayout clayout=null;

        if(tipologia.compareTo("Home")==0)
            clayout = (CoordinatorLayout) findViewById(R.id.clayout);
        else if(tipologia.compareTo("Sport")==0)
            clayout = (CoordinatorLayout) findViewById(R.id.clayout_sport);
        else if(tipologia.compareTo("Concerto")==0)
            clayout = (CoordinatorLayout) findViewById(R.id.clayout_con);
        else if(tipologia.compareTo("Cinema")==0)
            clayout = (CoordinatorLayout) findViewById(R.id.clayout_cin);
        else if(tipologia.compareTo("Fiera")==0)
            clayout = (CoordinatorLayout) findViewById(R.id.clayout_fiera);
        else if(tipologia.compareTo("Teatro")==0)
            clayout = (CoordinatorLayout) findViewById(R.id.clayout_teatro);

        snackbar = Snackbar.make(clayout, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getColor(R.color.colorPrimaryDark));
        tv =  snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        // tv.setTextSize(16);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }





    public void GoToProfilo() {
        Intent i = new Intent(MainActivity.this, Profilo.class);
        startActivity(i);
    }
    
    public void GoToAccesso() {
        Intent i = new Intent(MainActivity.this,Accesso.class);
        startActivity(i);
    }

    public void GoToCercaEvento() {
        Intent i = new Intent(MainActivity.this,CercaEvento.class);
        startActivity(i);
    }

    public void GoToInfo() {
        Intent i = new Intent(MainActivity.this,Info.class);
        startActivity(i);
    }

    public void GoToImpostazioni() {
        Intent i = new Intent(MainActivity.this,Impostazioni.class);
        startActivity(i);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongConstant")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
          Toast my_Toast;
          TextView v;
          View view;

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.i("Messaggi", "selezionato qualcosa nel menu items");

        if (id == R.id.nav_categorie) {
            my_Toast = Toast.makeText(this, "Cinema\nConcerto\nFiera\nSport\nTeatro", Toast.LENGTH_LONG);
            v = (TextView) my_Toast.getView().findViewById(android.R.id.message);
            view = my_Toast.getView();
            view.getBackground().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
            v.setTextColor(Color.WHITE);
            my_Toast.show();

        } else if (id == R.id.nav_cercaevento) {
                 GoToCercaEvento();
        }else if (id == R.id.nav_share) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "http://ingsoftw.eu-central-1.elasticbeanstalk.com");
            startActivity(Intent.createChooser(intent, "Condividi con"));

        }  else if (id == R.id.login) {
              GoToAccesso();
        } else if (id == R.id.profile) {
            GoToProfilo();
        }
        else if(id==R.id.impostazioni){
            GoToImpostazioni();
        }
        else if(id==R.id.info){
            GoToInfo();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OrderByData(ArrayList L){

        Collections.sort(L, new Comparator<MyListObject>() {
            @Override
            public int compare(MyListObject data1, MyListObject data2) {
                if( data1.getData().compareTo(data2.getData()) > 0 )
                    return 1;
                else if( data1.getData().compareTo(data2.getData()) < 0 )
                    return -1;
                else
                    return 0;
            }
        });

        myDialog.dismiss();
    }

    public void OrderByName(ArrayList L){
      // Log.i("messaggi","eventList in OrderByName prima : "+eventList);
        Collections.sort(L, new Comparator<MyListObject>() {
            @Override
            public int compare(MyListObject data1, MyListObject data2) {
                if( data1.getName().compareTo(data2.getName()) > 0 )
                    return 1;
                else if( data1.getName().compareTo(data2.getName()) < 0 )
                    return -1;
                else
                    return 0;
            }
        });
        Log.i("messaggi","eventList ordinata tramite nome");

        myDialog.dismiss();
    }

    public void OrderByPrezzo(ArrayList L){
        // Log.i("messaggi","eventList in OrderByName prima : "+eventList);
        Collections.sort(L, new Comparator<MyListObject>() {
            @Override
            public int compare(MyListObject data1, MyListObject data2) {
                if( data1.getCosto() > data2.getCosto())
                    return 1;
                else if( data1.getCosto() < data2.getCosto() )
                    return -1;
                else
                    return 0;
            }
        });
        Log.i("messaggi","eventList ordinata tramite Prezzo");
        myDialog.dismiss();
    }



    public void CreateByTipologia(){

        for(MyListObject o: eventList_begin){
            if(o.getTipologia().compareTo("cinema")==0)
                eventListCinema.add(o);
            else if(o.getTipologia().compareTo("concerto")==0)
                eventListConcerto.add(o);
            else if(o.getTipologia().compareTo("fiera")==0)
                eventListFiera.add(o);
            else if(o.getTipologia().compareTo("sport")==0)
                eventListSport.add(o);
            else if(o.getTipologia().compareTo("teatro")==0)
                eventListTeatro.add(o);
        }
    }

         public void getUser(String jsonData){


            if (jsonData != null) {

                JSONObject j = null;
                try {
                    j = new JSONObject(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    userCognome=(j.getString("cognome"));
                    userNome=(j.getString("nome"));
                    userUsername=(j.getString("username"));
                    nav_title.setText(userUsername);
                    nav_mail.setText(userNome);
                    //userImage=(j.getString("immagine"));    //una uri
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("messaggi", "Errore.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Impossibile caricare i dati utente. Controlla la tua connessione internet",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

        }





    /**
     * Async task class to get json by making HTTP call
     */
    private class GetEvents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.i("messaggi", "MAINACTIVITY : BackGround");

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //String jsonStr= "[{id:1,nome:VASCOROSSI,localita:ITALIA,data:2018-08-1,costo:0.0,tipologia:concerto},{id:2,nome:EMINEM,localita:AMERICA,data:2018-08-2,costo:0.0,tipologia:concerto},{id:3,nome:ALEXBRITTI,localita:LONDRA,data:2018-08-3,costo:0.0,tipologia:incontro},{id:4,nome:CAPAREZZA,localita:LONDRA,data:2018-08-4,costo:40.0,tipologia:fiera}]";

            if (jsonStr != null) {
                try {

                    JSONArray events = new JSONArray(jsonStr);
                    eventiTotali = events.length();
                    // looping through All Events
                    for (int i = 0; i < eventiTotali; i++) {

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



                        eventList.add(item);
                    }

                    Log.i("messaggi", "Lista creata dopo aver scorso tutti gli eventi");
                    eventList_begin = eventList;
                    CreateByTipologia();
                    // Log.i("messaggi", "Lista : "+eventList);
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

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            Log.i("messaggi", "MAINACTIVITY : Execute");

            if(mode== Mode.LIST) {

                FragmentList fragment = new FragmentList();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();

                FragmentListCinema fragment_cin = new FragmentListCinema();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_cinema,fragment_cin).commit();

                FragmentListConcerto fragment_con = new FragmentListConcerto();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_concerto, fragment_con).commit();

                FragmentListFiera fragment_fiera = new FragmentListFiera();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_fiera, fragment_fiera).commit();

                FragmentListSport fragment_sport = new FragmentListSport();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_sport, fragment_sport).commit();

                FragmentListTeatro fragment_teatro = new FragmentListTeatro();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_teatro, fragment_teatro).commit();

            }
           else if(mode== Mode.IMAGES){

                FragmentGrid_1 fragment = new FragmentGrid_1();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();

                FragmentGrid_1_Cinema fragment_cin = new FragmentGrid_1_Cinema();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_cinema, fragment_cin).commit();

                FragmentGrid_1_Concerto fragment_con = new FragmentGrid_1_Concerto();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_concerto, fragment_con).commit();

                FragmentGrid_1_Fiera fragment_fiera = new FragmentGrid_1_Fiera();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_fiera, fragment_fiera).commit();

                FragmentGrid_1_Sport fragment_sport = new FragmentGrid_1_Sport();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_sport, fragment_sport).commit();

                FragmentGrid_1_Teatro fragment_teatro = new FragmentGrid_1_Teatro();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_teatro, fragment_teatro).commit();
            }
            else{
                FragmentGrid_2 fragment = new FragmentGrid_2();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();

                FragmentGrid_2_Cinema fragment_cin = new FragmentGrid_2_Cinema();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_cinema, fragment_cin).commit();

                FragmentGrid_2_Concerto fragment_con = new FragmentGrid_2_Concerto();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_concerto, fragment_con).commit();

                FragmentGrid_2_Fiera fragment_fiera = new FragmentGrid_2_Fiera();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_fiera, fragment_fiera).commit();

                FragmentGrid_2_Sport fragment_sport = new FragmentGrid_2_Sport();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_sport, fragment_sport).commit();

                FragmentGrid_2_Teatro fragment_teatro = new FragmentGrid_2_Teatro();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_teatro, fragment_teatro).commit();
            }

        }
    }

}
