package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Model;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Registrazione;
import java.util.Calendar;


//Date pop up
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

     public static int year;
     public static int month;
     public static int day;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        Log.i("Messaggi", "anno : "+year);
        Log.i("Messaggi", "mese : "+month);
        Log.i("Messaggi", "giorno : "+day);
        this.year=year;
        this.month=month;
        this.day=day;

        Registrazione.textDate.setText(year+"-"+month+"-"+day);
        Registrazione.flag=true;
    }
}

