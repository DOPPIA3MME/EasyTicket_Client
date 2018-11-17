package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import net.glxn.qrgen.android.QRCode;

public class QRCODE extends AppCompatActivity {

    Dialog myDialog;
    public static String qrcode;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        myDialog = new Dialog(this);
        ImageView imageqrcode=findViewById(R.id.imageQRCODE);

        Bitmap lBitmap= QRCode.from(qrcode).bitmap();
        imageqrcode.setImageBitmap(lBitmap);
    }
}
