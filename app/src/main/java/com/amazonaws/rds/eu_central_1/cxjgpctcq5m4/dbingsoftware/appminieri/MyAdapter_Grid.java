package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter_Grid extends ArrayAdapter<MyListObject> {
    ArrayList<MyListObject> eventList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;


    public MyAdapter_Grid(Context context, int resourceID, ArrayList<MyListObject> values) {
        super(context, resourceID, values);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resourceID;
        eventList = values;
    }

    static class ViewHolder {
        public ImageView imageview;
        public ImageView imageview_icona;
        public TextView tvName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.image_event);
            holder.imageview_icona=(ImageView) v.findViewById(R.id.icona_sottotipo);
            holder.tvName = (TextView) v.findViewById(R.id.nome);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.tvName.setText(eventList.get(position).getName());
        Picasso.get().load(eventList.get(position).getImage()).placeholder(R.drawable.logo).error(R.drawable.notfound).into(holder.imageview);

       if(eventList.get(position).getSotto_tipologia().compareTo("Calcio")==0)
            Picasso.get().load(R.drawable.calcio_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Tennis")==0)
           Picasso.get().load(R.drawable.tennis_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Basket")==0)
           Picasso.get().load(R.drawable.basket_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Football")==0)
           Picasso.get().load(R.drawable.football_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Lotta")==0)
           Picasso.get().load(R.drawable.lotta_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Baseball")==0)
           Picasso.get().load(R.drawable.baseball_black).into(holder.imageview_icona);

       else if(eventList.get(position).getSotto_tipologia().compareTo("Comico")==0)
           Picasso.get().load(R.drawable.comico_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Azione")==0)
           Picasso.get().load(R.drawable.azione_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Romantico")==0)
           Picasso.get().load(R.drawable.romantico_black).into(holder.imageview_icona);

       else if(eventList.get(position).getSotto_tipologia().compareTo("Solista")==0)
            Picasso.get().load(R.drawable.microfono).into(holder.imageview_icona);
        else if(eventList.get(position).getSotto_tipologia().compareTo("Dj Set")==0)
            Picasso.get().load(R.drawable.dj_set).into(holder.imageview_icona);
        else if(eventList.get(position).getSotto_tipologia().compareTo("Orchestra")==0)
            Picasso.get().load(R.drawable.orchestra).into(holder.imageview_icona);

       else if(eventList.get(position).getSotto_tipologia().compareTo("Gaming")==0)
           Picasso.get().load(R.drawable.gaming_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Cibo")==0)
           Picasso.get().load(R.drawable.cibo_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Cultura")==0)
           Picasso.get().load(R.drawable.cultura_black).into(holder.imageview_icona);

       else if(eventList.get(position).getSotto_tipologia().compareTo("Opera")==0)
           Picasso.get().load(R.drawable.opera_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Commedia")==0)
           Picasso.get().load(R.drawable.comico_black).into(holder.imageview_icona);
       else if(eventList.get(position).getSotto_tipologia().compareTo("Drammatico")==0)
           Picasso.get().load(R.drawable.drammatico_black).into(holder.imageview_icona);

       else{
           Picasso.get().load(R.drawable.back).into(holder.imageview_icona);
       }


        return v;
    }
}