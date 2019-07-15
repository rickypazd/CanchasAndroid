package com.ricardopazdemiquel.appcanchas;

import com.ricardopazdemiquel.appcanchas.model.Cancha;
import com.ricardopazdemiquel.appcanchas.R;

import java.util.ArrayList;
import java.util.List;

public class ListaCanchas {

    private static ListaCanchas instancia;
    private List<Cancha> listaCanchas;

    public static ListaCanchas getInstancia() {
        if (instancia == null)
            instancia = new ListaCanchas();

        return instancia;
    }

    private ListaCanchas() {
        this.listaCanchas = new ArrayList<>();
        listaCanchas.add(new Cancha(1, "La Bombonera", "Santa Cruz de la Sierra", "Av. Angamos Este 1551", "En La Bombonera contamos con alquiler de canchas deportivas de grass sintético y techadas, canchas adecuadas para el entrenamiento de diferentes tipos de deporte y personal calificado para ofrecerle un servicio con calidad...", "[591]-77800221", "bombobera@peloteada.com", "10 k", R.mipmap.cancha2));
        listaCanchas.add(new Cancha(2, "Cancha Amboro", "Santa Cruz de la Sierra", "Entre 25 de Mayo y Casilda , Villa Lynch, San Martín, Buenos Aire", "En La Bombonera contamos con alquiler de canchas deportivas de grass sintético y techadas, canchas adecuadas para el entrenamiento de diferentes tipos de deporte y personal calificado para ofrecerle un servicio con calidad.Ofrecemos atención a eventos empresariales y deportivos.", "[591]-72659258", "amboro@peloteada.com", "10 k", R.mipmap.cancha3));
        listaCanchas.add(new Cancha(3, "Mayor San Pablo Soccer - Canchas Sintéticas", "Santa Cruz de la Sierra", "Av. Angamos Este 1551", "En La Bombonera contamos con alquiler de canchas deportivas de grass sintético y techadas, canchas adecuadas para el entrenamiento de diferentes tipos de deporte y personal calificado para ofrecerle un servicio con calidad...", "[591]-77800221", "bombobera@peloteada.com", "10 k", R.mipmap.cancha4));
    }

    public List<Cancha> getListaCanchas() {
        return listaCanchas;
    }

    public void setListaCanchas(List<Cancha> listaCanchas) {
        this.listaCanchas = listaCanchas;
    }

}
