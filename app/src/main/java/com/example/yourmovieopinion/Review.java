package com.example.yourmovieopinion;

import java.util.Date;

public class Review {
    String usuario;
    String titulo;
    String review;
    Date fecha;

    public Review(String usuario, String titulo, String review, Date fecha) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.review = review;
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
