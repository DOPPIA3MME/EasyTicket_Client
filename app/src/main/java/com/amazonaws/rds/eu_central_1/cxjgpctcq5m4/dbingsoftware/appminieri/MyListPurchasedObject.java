package com.amazonaws.rds.eu_central_1.cxjgpctcq5m4.dbingsoftware.appminieri;


//Gli oggetti che creo avranno solo nome e immagine
public class MyListPurchasedObject {

   private String id;
 private String name;
 private String data;
 private float costo;
 private String localita;
 private String tipologia;
 private String sotto_tipo;
 private String indirizzo;
   private String image;
 private String user_id;
 private String acquisto_code;

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setTipologia(String tipologia) { this.tipologia = tipologia; }
    public String getTipologia() { return tipologia; }

    public void setData(String data) { this.data = data; }
    public String getData() { return data; }

    public void setLocalita(String localita) { this.localita = localita; }
    public String getLocalita() { return localita; }

    public void setCosto(float costo) { this.costo = costo; }
    public float getCosto() {
        return costo;
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }

    public void setIndirizzo(String indirizzo){ this.indirizzo = indirizzo;}
    public String getIndirizzo() { return indirizzo; }

    public void setSotto_tipologia(String sotto_tipo){ this.sotto_tipo = sotto_tipo;}
    public String getSotto_tipologia() { return sotto_tipo; }

    public void setAcquisto_code(String acquisto_code){ this.acquisto_code = acquisto_code;}
    public String getAcquisto_code() { return acquisto_code; }

     public void setUser_id(String user_id){ this.user_id = user_id;}
     public String getUser_id() { return user_id; }

    public MyListPurchasedObject getThisObject(){return this;}

    public String toString(){
        return "{localita="+localita+", data="+data+", costo="+costo+", nome="+name+", id="+id+", tipologia="+tipologia+"}";
    }

}