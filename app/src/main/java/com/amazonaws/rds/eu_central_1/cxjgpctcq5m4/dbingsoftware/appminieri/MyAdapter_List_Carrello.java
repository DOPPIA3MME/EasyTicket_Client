package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri.Fase1.Accesso;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class MyAdapter_List_Carrello extends ArrayAdapter<MyListObject> {

    private final Context context;
    private final List<MyListObject> values;
    private int resourceID;



    public MyAdapter_List_Carrello(Context context, int resourceID, List<MyListObject> values) {
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
        ImageView remove_button;
        ImageView buy_button;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;
        final MyListObject[] item = new MyListObject[1];

    if(values!=null){
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceID, null);
            holder = new ViewHolder();
            holder.nome = (TextView) convertView.findViewById(R.id.nome_carrello);
            holder.costo = (TextView) convertView.findViewById(R.id.costo_carrello);
            holder.data = (TextView) convertView.findViewById(R.id.data_carrello);
            holder.localita = (TextView) convertView.findViewById(R.id.localita_carrello);
            holder.tipologia = (TextView) convertView.findViewById(R.id.tipologia_carrello);
            holder.imageview=(ImageView)  convertView.findViewById(R.id.image_carrello);
            holder.buy_button=(ImageView) convertView.findViewById(R.id.buttonCompra);
            holder.remove_button=(ImageView) convertView.findViewById(R.id.remove_carrello);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        MyListObject rowItem = (MyListObject) values.get(position);
        holder.nome.setText(rowItem.getName());
        holder.data.setText(rowItem.getData());
        holder.costo.setText(rowItem.getCosto()+" €");
        holder.localita.setText(rowItem.getLocalita().toUpperCase());
        holder.tipologia.setText(rowItem.getTipologia());
        Picasso.get().load(MainActivity.eventListCarrello.get(position).getImage()).placeholder(R.drawable.logo).error(R.drawable.notfound).fit().into(holder.imageview);

        holder.buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 // Toast.makeText(getApplicationContext(), "Compro : "+v.getTag(), Toast.LENGTH_LONG).show();
                  Pagamento.evento= (MyListObject) v.getTag();
                  GoToPagamento();
            }
        });

        holder.remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Cancello : "+v.getTag(), Toast.LENGTH_LONG).show();
                 item[0] = (MyListObject) v.getTag();
                MainActivity.eventListCarrello.remove((MyListObject)v.getTag());
                MainActivity.eventListCarrelloId.remove(((MyListObject)v.getTag()).getId());
                FragmentCarrello.totale=FragmentCarrello.totale-item[0].getCosto();

                String userCarrelloDelete="http://ingsoftw.eu-central-1.elasticbeanstalk.com/carrello/delete/item/"+item[0].getId();
                Request request=new Request.Builder().url(userCarrelloDelete).build();
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
                        }
                        else {
                            Log.i("messaggi", "response FALLITO : " + response);
                        }
                    }
                });


                 Void v1=null;
                 CarrelloMainActivity.g.onPostExecute(v1);
            }
        });

        holder.buy_button.setTag(rowItem);
        holder.remove_button.setTag(rowItem);
       }

        return convertView;
    }

    public void GoToPagamento() {
        Intent intent = new Intent(getContext(), Pagamento.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }
}

