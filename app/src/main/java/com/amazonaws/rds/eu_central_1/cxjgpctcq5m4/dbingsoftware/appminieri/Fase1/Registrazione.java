package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.MainActivity;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Model.DatePickerFragment;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.ConfermaTramiteMail;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.InputValidation;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Model.User;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.R;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Sql.DatabaseHelper;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.facebook.FacebookSdk;



public class Registrazione extends AppCompatActivity {

    private final AppCompatActivity activity = Registrazione.this;
    private CallbackManager callbackManager;


    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutNumber;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutSurname;
    private TextInputLayout textInputLayoutPaese;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutPassword2;


    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextNumber;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextPaese;
    private TextInputEditText textInputEditTextSurname;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextPassword2;

    public static TextView textDate;

    public static String textName="Nome vuoto";
    private String textNumber;
    private String textSurname;
    private String textPaese;
    private String textPass1;
    private String textPass2;
    public static String textEmail="Email vuota";


    public static boolean flag=false;         //DataPickerFragment, set data
    public static String textSended="";

    Toast my_Toast;
    TextView v;
    View view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        initViews();
        initObjects();

     AppCompatButton buttonDate = findViewById(R.id.buttonDate);
     buttonDate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
                SetDate();
         }
     });

        textDate=findViewById(R.id.textDate);



        //FACEBOOK LOG
        callbackManager = CallbackManager.Factory.create();

        LoginButton fbLoginButton = (LoginButton) findViewById(R.id.FacebookButton);
        // fbLoginButton.setReadPermissions("email");

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Accesso Effettuato!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Accesso non effettuato!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




          AppCompatButton buttonRegister=findViewById(R.id.appCompatButtonRegister);
          buttonRegister.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  AllChecks();
              }
          });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

       // AccessToken accessToken = AccessToken.getCurrentAccessToken();
      //  boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
       // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }



    public void CreateBottomBar(){

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.facebook:
                                //Intent i = new Intent(Registrazione.this,LoginFacebook.class);
                               // startActivity(i);
                                break;

                            case R.id.instagram:


                                break;

                            case R.id.google:

                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });


        //colori originali
        bottomNavigationView.setItemIconTintList(null);
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {

        textInputEditTextEmail =  findViewById(R.id.textInputEditTextEmail);
        textInputEditTextNumber =  findViewById(R.id.textInputEditTextNumber);
        textInputEditTextName =  findViewById(R.id.textInputEditTextName);
        textInputEditTextPaese =  findViewById(R.id.textInputEditTextPaese);
        textInputEditTextSurname =  findViewById(R.id.textInputEditTextSurname);
        textInputEditTextPassword =  findViewById(R.id.textInputEditTextPassword);
        textInputEditTextPassword2 =  findViewById(R.id.textInputEditTextPassword2);

        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail =  findViewById(R.id.textInputLayoutEmail);
        textInputLayoutSurname =  findViewById(R.id.textInputLayoutSurname);
        textInputLayoutNumber =  findViewById(R.id.textInputLayoutNumber);
        textInputLayoutPaese =  findViewById(R.id.textInputLayoutPaese);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutPassword2 =  findViewById(R.id.textInputLayoutPassword2);
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }




    public void GoToConfermaTramiteMail() {
        Log.i("Messaggi", "Entrato in ConfermaTramiteMail");
        Intent i = new Intent(Registrazione.this, ConfermaTramiteMail.class);
        startActivity(i);
    }



    private void AllChecks() {

        Log.i("Messaggi", "Entrato in AllChecks");

        textNumber=textInputLayoutNumber.getEditText().getText().toString().trim();
        textName=textInputLayoutName.getEditText().getText().toString().trim();

        textPaese=textInputLayoutPaese.getEditText().getText().toString().trim();
        textSurname=textInputLayoutSurname.getEditText().getText().toString().trim();
        textEmail=textInputLayoutEmail.getEditText().getText().toString().trim();
        textPass1=textInputLayoutPassword.getEditText().getText().toString().trim();
        textPass2=textInputLayoutPassword2.getEditText().getText().toString().trim();


        if (textName.isEmpty()) {
            textInputEditTextName.setError(getString(R.string.error_message_empty));
            Toast.makeText(getApplicationContext(), "Campo Nome vuoto!", Toast.LENGTH_LONG).show();
            return;
        }
        if (textSurname.isEmpty()) {
            textInputEditTextSurname.setError(getString(R.string.error_message_empty));
            Toast.makeText(getApplicationContext(), "Campo Cognome vuoto!", Toast.LENGTH_LONG).show();
            return;
        }

        if (textPaese.isEmpty()) {
            textInputEditTextPaese.setError(getString(R.string.error_message_empty));
            Toast.makeText(getApplicationContext(), "Campo Paese vuoto!", Toast.LENGTH_LONG).show();
            return;
        }

        if (textEmail.isEmpty()) {
            textInputEditTextEmail.setError(getString(R.string.error_message_empty));
            Toast.makeText(getApplicationContext(), "Campo Mail vuoto!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            Toast.makeText(getApplicationContext(), "Inserisci una mail valida!", Toast.LENGTH_LONG).show();
            return;
        }

        if (textNumber.isEmpty()) {
            textInputEditTextNumber.setError(getString(R.string.error_message_empty));                        //textInputLayoutNumber per scriverlo sotto
            Toast.makeText(getApplicationContext(), "Campo Numero vuoto!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!isValidPhone(textNumber)) {
            textInputEditTextNumber.setError(getString(R.string.error_message_number));
            Toast.makeText(getApplicationContext(), "Inserisci un numero valido!", Toast.LENGTH_LONG).show();
            return;
        }

        if(!flag){
            Toast.makeText(getApplicationContext(), "Campo Data vuoto\nClicca sul campo per selezionare la tua data di nascita", Toast.LENGTH_LONG).show();
            return;
        }


        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextPassword2,
                textInputLayoutPassword2, getString(R.string.error_password_match))) {
            return;
        }



        Log.i("messaggi","Text Date : "+textDate.getText());

   //Effettuo una richiesta http al server con i dati immessi dall'utente
       final String serverRequest="http://ingsoftw.eu-central-1.elasticbeanstalk.com/registration?" +
                "nome="+textName+"&cognome="+textSurname+"&data="+textDate.getText()+"&paese="+textPaese+
                "&telefono="+textNumber+"&username="+textEmail+"&password="+textPass1+"&password-rip="+textPass2;

       // http://ingsoftw.eu-central-1.elasticbeanstalk.com/registration?nome=Luigi&cognome=Coppola&data=2018-08-29&paese=Napoli&telefono=33333333&username=coppo&password=coppo&password-rip=coppo

        //RICHIESTA GET
       OkHttpClient client=new OkHttpClient();
       Request request=new Request.Builder().url(serverRequest).build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               e.printStackTrace();
               Log.i("messaggi","FAILURE");
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
                   if(response.isSuccessful()) {
                       Log.i("messaggi", "response ACCETTATO : " + response);
                       textSended=RandomText(10);
                       new Handler(Looper.getMainLooper()).post(new Runnable() {
                           @RequiresApi(api = Build.VERSION_CODES.M)
                           @Override
                           public void run() {
                               my_Toast = Toast.makeText(Registrazione.this, "Profilo creato, usa la tua mail come username", Toast.LENGTH_LONG);
                               v = (TextView) my_Toast.getView().findViewById(android.R.id.message);
                               view = my_Toast.getView();
                               view.getBackground().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                               v.setTextColor(Color.WHITE);
                               my_Toast.show();
                           }
                       });
                       GoToAccesso();
                   }
                   else
                       Log.i("messaggi","response FALLITO : "+response);
           }
       });

    }


    public void GoToAccesso() {
        Log.i("Messaggi", "Entrato in Accesso");
        Intent i = new Intent(Registrazione.this,Accesso.class);
        startActivity(i);
       // MainActivity.profiloEntered=true;
    }


    public String RandomText(int l){

        String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random RANDOM = new Random();


        StringBuilder sb = new StringBuilder(l);

        for (int i = 0; i < l; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }

    public boolean isValidPhone(String phoneNumber) {

        String regex = "^\\+?[0-9. ()-]{10,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) return true;
        else         return false;

    }

    public void SetDate() {

        Log.i("Messaggi", "flag="+flag);
        Log.i("Messaggi", "Entrato in SetDate");
       /* DialogFragment newFragment = new TimePickerFragment();                //Create time pop up
        newFragment.show(getSupportFragmentManager(), "TimePicker");*/
        DialogFragment newFragment2=new DatePickerFragment();
        newFragment2.show(getSupportFragmentManager(), "DatePicker");
    }


}




