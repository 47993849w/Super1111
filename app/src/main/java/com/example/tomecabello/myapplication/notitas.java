package com.example.tomecabello.myapplication;

/**
 * Created by tomeCabello on 29/02/2016.
 */
public class notitas {

    String lat;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    String lo;
    public notitas(String mensa, String titulo) {
        this.mensa = mensa;
        this.titulo = titulo;
    }

    public String getUr() {
        return ur;
    }

    public void setUr(String ur) {
        this.ur = ur;
    }

    String ur;

    public notitas(){

    }

    public String getMensa() {
        return mensa;
    }

    public void setMensa(String mensa) {
        this.mensa = mensa;
    }

    String mensa;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    String titulo;

}
