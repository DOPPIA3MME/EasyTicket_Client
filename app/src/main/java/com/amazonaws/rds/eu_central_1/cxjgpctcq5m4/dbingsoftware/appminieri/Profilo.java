package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Accesso;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Profilo extends AppCompatActivity {

    Dialog myDialog;
    public static Context context;
    public static final int GET_FROM_GALLERY = 3;
    private  TextView textUsername;
    private TextView textNome;
    private TextView textCognome;
    private ImageView imageViewProfilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);
        myDialog = new Dialog(this);
        context=getApplicationContext();

       Button buttonLogout=findViewById(R.id.buttonLogout);
       buttonLogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ShowPopupLogout();
           }
       });

        textUsername=findViewById(R.id.usernameprofilo);
        textNome=findViewById(R.id.nomeprofilo);
        textCognome=findViewById(R.id.cognomeprofilo);
        imageViewProfilo = (ImageView) findViewById(R.id.imageViewProfilo);

        textUsername.setText(MainActivity.userUsername);
        textNome.setText(MainActivity.userNome);
        textCognome.setText(MainActivity.userCognome);
        Picasso.get().load(MainActivity.userImage).noPlaceholder().centerCrop().fit().into(imageViewProfilo);

    }




    public void GoToImpostazioni(View view) {
        Log.i("Messaggi","Entrato in Impostazioni");
        Intent i=new Intent(Profilo.this,Impostazioni.class);
        startActivity(i);
    }


    //Crea il menu delle opzioni
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("Messaggi","Creato il menu delle opzioni");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_impostazioni, menu);
        return true;
    }

    //gestisce il menu delle opzioni
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("Messaggi","selezionato qualcosa nel menu opzioni");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if(id==R.id.impostazioni_profilo){
               ShowPopup2();
         }

        return super.onOptionsItemSelected(item);
    }



    public void ShowPopupLogout(){
        Button btnYes;

        myDialog.setContentView(R.layout.activity_popup_exit);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();




        btnYes=(Button) myDialog.findViewById(R.id.buttonYes);
        btnYes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                String logoutRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/logout";
                Request request = new Request.Builder().url(logoutRequest).build();
                Accesso.client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.i("messaggi", "FAILURE");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Log.i("messaggi", "response ACCETTATO : " + response);
                        } else {
                            Log.i("messaggi", "response FALLITO : " + response);
                        }
                    }
                });

                MainActivity.profiloEntered=false;
                Intent i=new Intent(Profilo.this,MainActivity.class);
                startActivity(i);
            }
        });


    }


    public void ShowPopup2(){


        myDialog.setContentView(R.layout.activity_popup_profilo);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


       TextView changePI=myDialog.findViewById(R.id.changeprofileimage);
       changePI.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
               myDialog.dismiss();
             }});
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            MainActivity.userImage=data.getData();

            /*
            DEVO MANDARE LA Uri nel database tramite server
             */

            Picasso.get().load(selectedImage).noPlaceholder().centerCrop().fit().into(imageViewProfilo);
        }
    }

}
