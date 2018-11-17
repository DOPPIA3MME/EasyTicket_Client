package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Registrazione;


public class ConfermaTramiteMail extends AppCompatActivity {


    private TextInputEditText textInputEditTextConfirmMail;
    private TextInputLayout textInputLayoutConfirmMail;
    private String textConfirmMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferma_tramite_mail);
    }

    public void CheckMail(View v){
        Log.i("Messaggi", "Entrato in CheckMail");



        textInputEditTextConfirmMail=findViewById(R.id.textInputEditTextConfirmMail);
        textInputLayoutConfirmMail = findViewById(R.id.textInputLayoutConfirmMail);
        textConfirmMail=textInputLayoutConfirmMail.getEditText().getText().toString().trim();


        Log.i("Messaggi", "textSended"+ Registrazione.textSended);
        if(textConfirmMail.isEmpty())
            textInputEditTextConfirmMail.setError(getString(R.string.error_message_empty));
        else if(Registrazione.textSended.compareTo(textConfirmMail)==0)
           GoToMainActivity();
        else
           textInputEditTextConfirmMail.setError(getString(R.string.error_message_wrongtext));


    }

    public void GoToMainActivity() {
        Log.i("Messaggi", "Entrato in MainActivity");
        Intent i = new Intent(ConfermaTramiteMail.this,MainActivity.class);
        startActivity(i);
        MainActivity.profiloEntered=true;
    }


}
