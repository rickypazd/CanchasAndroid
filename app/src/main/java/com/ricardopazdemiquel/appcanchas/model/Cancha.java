package com.ricardopazdemiquel.appcanchas.model;

public class Cancha {

    private int id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String descripcion;
    private String numero;
    private String correo;
    private String valoracion;
    private int imagen;

    public Cancha() {
    }

    public Cancha(int id, String nombre, String ciudad, String direccion, String descripcion, String numero, String correo, String valoracion, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.numero = numero;
        this.correo = correo;
        this.valoracion = valoracion;
        this.imagen = imagen;
    }

    //<editor-fold desc="Getters y Setters">

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    //</editor-fold>

}
