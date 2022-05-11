package com.example.yourmovieopinion.database;

import com.example.yourmovieopinion.objetos.Pelicula;
import com.example.yourmovieopinion.objetos.Review;
import com.example.yourmovieopinion.objetos.Usuario;

import java.sql.Connection;
import java.util.List;

public interface MovRevDao {
    public boolean inicioSesion(String password,String user)  ;

    public Usuario obtenerUser(String user, String password, String email) ;

    public Connection connectDB() ;

    public boolean registrarUsuario(String nombre, String password, String email)  ;

    public boolean guardarReview(String usuario, String review, String pelicula) ;

    public List<Review> mostrarReviews(Pelicula pelicula);

    public Boolean eliminarReview(String usuario, String pelicula);

    public Boolean existeReview(String name,String movie) ;

    public Boolean actualizarReview(String texto,String usuario,String pelicula);

    public boolean eliminarReviewsPelicula(String title);

}
