package com.hammer67.watsappclone.activities.models;

public class Usuario {

    private String uid, nombre, correo, imageUrl, estado;
    private long timestampRegistro;

    public Usuario() {

    }

    public Usuario(String uid, String nombre, String correo,long timestampRegistro) {
        this.uid = uid;
        this.nombre = nombre;
        this.correo = correo;
        this.imageUrl = "";
        this.estado = "";
        this.timestampRegistro = timestampRegistro;
    }

    public String getUid() {
        return uid;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getEstado() {
        return estado;
    }

    public long getTimestampRegistro() {
        return timestampRegistro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "uid='" + uid + '\'' +
                ", nombre='" + nombre + '\'' +
                ", timestampRegistro=" + timestampRegistro +
                '}';
    }
}
