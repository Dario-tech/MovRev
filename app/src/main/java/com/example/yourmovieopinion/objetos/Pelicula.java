package com.example.yourmovieopinion.objetos;

public class Pelicula {

    private String titulo;
    private int imagen;
    private String imagen_web;

    public Pelicula(String titulo, int imagen) {
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public Pelicula(String titulo, String imagen_web) {
        this.titulo = titulo;
        this.imagen_web = imagen_web;
    }
    public Pelicula(){

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen_web;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
