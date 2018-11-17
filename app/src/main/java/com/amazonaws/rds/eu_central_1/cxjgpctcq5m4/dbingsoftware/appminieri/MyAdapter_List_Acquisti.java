package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MyAdapter_List_Acquisti extends ArrayAdapter<MyListObject> {

    private final Context context;
    private final List<MyListObject> values;
    private int resourceID;


    public MyAdapter_List_Acquisti(Context context, int resourceID, List<MyListObject> values) {
        super(context, resourceID, values);
        this.context = context;
        this.resourceID = resourceID;
        this.values = values;
    }

    private class ViewHolder {
        TextView nome;
        TextView costo;
        TextView data;
        TextView localita;
        TextView tipologia;
        ImageView imageview;
        ImageView show_button;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;

        if(values!=null) {
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resourceID, null);
                holder = new ViewHolder();
                holder.nome = (TextView) convertView.findViewById(R.id.nome_acquisti);
                holder.costo = (TextView) convertView.findViewById(R.id.costo_acquisti);
                holder.data = (TextView) convertView.findViewById(R.id.data_acquisti);
                holder.localita = (TextView) convertView.findViewById(R.id.localita_acquisti);
                holder.tipologia = (TextView) convertView.findViewById(R.id.tipologia_acquisti);
                holder.imageview = (ImageView) convertView.findViewById(R.id.image_acquisti);
                holder.show_button = (ImageView) convertView.findViewById(R.id.buttonShow);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MyListObject rowItem = (MyListObject) values.get(position);
            holder.nome.setText(rowItem.getName());
            holder.data.setText(rowItem.getData());
            holder.costo.setText(rowItem.getCosto() + " €");
            holder.localita.setText(rowItem.getLocalita().toUpperCase());
            holder.tipologia.setText(rowItem.getTipologia());
            Picasso.get().load(MainActivity.eventListAcquisti.get(position).getImage()).placeholder(R.drawable.logo).error(R.drawable.notfound).fit().into(holder.imageview);

            holder.show_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyListObject evento = (MyListObject)v.getTag();
                    QRCODE.qrcode=evento.getAcquisto_code();
                    GoToQRCODE();

                }
            });

            holder.show_button.setTag(rowItem);
        }

        return convertView;
    }


    public void GoToQRCODE() {
        Intent intent = new Intent(getContext(), QRCODE.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }


}

