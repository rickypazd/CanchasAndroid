package com.example.ricardopazdemiquel.appcanchas.model;

public class Horas {

    private int id;
    private int cantidad;


    public Horas() {
    }

    public Horas(int id, int cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
