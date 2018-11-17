package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Accesso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class Pagamento extends AppCompatActivity {

    Dialog myDialog;
    int flag=0;
    public static MyListObject evento;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pagamento);
         myDialog = new Dialog(this);



        final EditText mastercard=findViewById(R.id.mastercard);
        final EditText visa=findViewById(R.id.postepay);
        final EditText americanexpress=findViewById(R.id.americanexpress);
        Button confermaButton = findViewById(R.id.buttonConferma);




        visa.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    mastercard.setText("");
                    americanexpress.setText("");
                   flag=1;
                }
            }
        });

        mastercard.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    visa.setText("");
                    americanexpress.setText("");
                    flag=2;
                }
            }
        });

        americanexpress.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    mastercard.setText("");
                    visa.setText("");
                    flag=3;
                }
            }
        });


        confermaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                   if(  (flag==1 &&  visa.getText().toString().length()==19)  || (flag==3 &&  americanexpress.getText().toString().length()==19)){

                       ShowPopup_Success();
                   }
                   else if(flag==2 && mastercard.getText().toString().length()==19) {

                       String payRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/acquisto/"+Accesso.username+"/"+
                               "tok_cvcCheckFail/"+(int)evento.getCosto()*100+"/"+evento.getId();
                       Request request = new Request.Builder().url(payRequest).build();
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
                                   new Handler(Looper.getMainLooper()).post(new Runnable() {
                                       @RequiresApi(api = Build.VERSION_CODES.M)
                                       @Override
                                       public void run() {
                                           ShowPopup_Fail();
                                       }
                                   });
                               }
                           }
                       });
                   }
                   else{
                       Toast.makeText(getApplicationContext(), "Inserisci dati della carta validi", Toast.LENGTH_SHORT).show();
                   }

            }
        });

        mastercard.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Remove spacing char
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });

        americanexpress.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Remove spacing char
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });

        visa.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Remove spacing char
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });

    }


    public void ShowPopup_Success(){

        myDialog.setContentView(R.layout.activity_popup_success);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        Button okButton = myDialog.findViewById(R.id.buttonOkSuccess);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                finish();
            }
        });

        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
               finish();
            }
        });

        if(FragmentCarrello.acquistaTutto==true){

            for(int i=0;i<MainActivity.eventListCarrello.size();i++) {
                MainActivity.eventListAcquisti.add(MainActivity.eventListCarrello.get(i));


                String payRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/acquisto/"+Accesso.username+"/"+
                        "tok_visa/"+(int)MainActivity.eventListCarrello.get(i).getCosto()*100+"/"+MainActivity.eventListCarrello.get(i).getId();
                Request request = new Request.Builder().url(payRequest).build();
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




                //elimino evento dal carrello
                String userCarrelloDelete="http://ingsoftw.eu-central-1.elasticbeanstalk.com/carrello/delete/item/"+MainActivity.eventListCarrello.get(i).getId();
                Request request2=new Request.Builder().url(userCarrelloDelete).build();
                Accesso.client.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.i("messaggi","FAILURE");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            Log.i("messaggi", "response ACCETTATO : " + response);
                        }
                        else {
                            Log.i("messaggi", "response FALLITO : " + response);
                        }
                    }
                });
            }



            MainActivity.eventListCarrello.clear();
            FragmentCarrello.totale = 0;


        }
        else {
            MainActivity.eventListAcquisti.add(evento);
            MainActivity.eventListCarrello.remove(evento);
            MainActivity.eventListCarrelloId.remove(evento.getId());
            FragmentCarrello.totale = FragmentCarrello.totale - evento.getCosto();


            String payRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/api/get/acquisto/"+Accesso.username+"/"+
                    "tok_visa/"+(int)evento.getCosto()*100+"/"+evento.getId();
            Request request = new Request.Builder().url(payRequest).build();
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

            String userCarrelloDelete = "http://ingsoftw.eu-central-1.elasticbeanstalk.com/carrello/delete/item/" + evento.getId();
            Request request2 = new Request.Builder().url(userCarrelloDelete).build();
            Accesso.client.newCall(request2).enqueue(new Callback() {
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
        }


        Void v1=null;
        CarrelloMainActivity.g.onPostExecute(v1);
    }

    public void ShowPopup_Fail(){


        myDialog.setContentView(R.layout.activity_popup_fail);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        Button okButton = myDialog.findViewById(R.id.buttonOkFail);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

    }


}
