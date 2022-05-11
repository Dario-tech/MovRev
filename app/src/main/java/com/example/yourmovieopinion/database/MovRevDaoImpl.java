package com.example.yourmovieopinion.database;

import com.example.yourmovieopinion.objetos.Pelicula;
import com.example.yourmovieopinion.objetos.Review;
import com.example.yourmovieopinion.objetos.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MovRevDaoImpl {


    private String host = "ec2-34-247-72-29.eu-west-1.compute.amazonaws.com";
    private int port = 5432;
    private String database = "d1p1edjm0sr6a2";
    private String user = "pkginoqsiaaqph";
    private String password = "a13efce2cff950d4b9114bfd71b4bdf771e5c81282d353104d379b7d3789c98a";
    private String url ="jdbc:postgresql://" + host + ":" + port + "/" + database;
    private java.util.Date date = new java.util.Date();
    MovRevDaoImpl()  {
        System.out.println("Instancia MovRevDAO creada:\n");
        System.out.println("\tParametros: " + host + ":" + port + "/" + database + "\n");
    }

    public Connection connectDB()  {

        Connection connection = null;
        try {
            // We register the PostgreSQL driver
            // Registramos el driver de PostgresSQL
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }

            // Database connect
            // Conectamos con la base de datos
            connection = DriverManager.getConnection(url, user, password);
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error al conectar con la base de datos de PostgreSQL (" + url + "): " + sqle);
        }
        return connection;
    }

    public boolean inicioSesion(String contrasena,String usuario)  {
        Connection connection = null;
        boolean bool = false;
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(url, user, password);
            //connection = connectDB();
            if (connection != null) {
                String sql = "select * from users where pass=? and user_name=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, contrasena);
                st.setString(2,usuario);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    System.out.println("Se ha encontrado el usuario: " + usuario);
                    bool = true;
                } else
                    System.out.println("No existe el usuario o contrasena incorrecta");
                rs.close();
                st.close();
                connection.close();
            } else {
                System.out.println("No se ha podido conectar con la base de datos.\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    public Usuario obtenerUser(String usuario, String contrasena,String email)  {
        Connection connection = null;
        Usuario datosUsuario= null;
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(url, user, password);
            //connection = connectDB();
            if (connection != null) {
                String sql = "select * from users where user_name=? and pass=? and email=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, usuario);
                st.setString(2, contrasena);
                st.setString(3, email);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    datosUsuario = new Usuario();
                    datosUsuario.setId(rs.getInt(1));
                    datosUsuario.setUserName(rs.getString(2));
                    datosUsuario.setEmail(rs.getString(3));
                    datosUsuario.setPass(rs.getString(4));

                } else
                    System.out.println("No existe el usuario o contrasena incorrecta");
                rs.close();
                st.close();
                connection.close();
            } else {
                System.out.println("No se ha podido conectar con la base de datos.\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datosUsuario;
    }

    public boolean registrarUsuario(String nombre, String password, String email)  {
        Connection connection;
        Boolean bool = false;

        try {
            connection = connectDB();
            if (connection != null) {
                String sql_select = "select * from users where user_name=? or email=?";
                PreparedStatement st_select = connection.prepareStatement(sql_select);
                st_select.setString(1, nombre);
                st_select.setString(2, email);
                ResultSet rs_select = st_select.executeQuery();

                if (!rs_select.next()) {
                    System.out.println("No existe ningun usuario con ese nombre o email.");

                    String sql_insert = "INSERT INTO users (user_name, email, pass) VALUES (?,?,?)";
                    PreparedStatement st_insert = connection.prepareStatement(sql_insert);

                    st_insert.setString(1, nombre);
                    st_insert.setString(2, email);
                    st_insert.setString(3, password);

                    if (st_insert.executeUpdate() == 1) {
                        System.out.println("Se ha insertado el usuario correctamente");
                        bool = true;
                    } else {
                        System.out.println("No se ha insertado el usuario");
                    }
                    st_insert.close();
                } else {
                    System.out.println("Ya existe un usuario con ese nombre o email.");
                }
                st_select.close();
                connection.close();
            } else {
                System.out.println("No se ha podido conectar con la base de datos.\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    public boolean guardarReview(String usuario, String review, String pelicula)  {
        Connection connection = null;
        boolean bool = false;

        try {
            //connection = connectDB();
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                String sql = "insert into reviews (user_name, movie, review, fec) values (?, ?, ?, ?)";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, usuario);
                st.setString(2, pelicula);
                st.setString(3, review);
                st.setDate(4, (Date) date); //Casteamos de java.util.date a java.sql.date
                if (st.executeUpdate() == 1) {
                    System.out.println("Se ha guardado la review correctamente");
                    bool = true;
                } else {
                    System.out.println("No se ha guardado la review");
                }

                st.close();
                connection.close();
            } else {
                System.out.println("No se ha podido conectar con la base de datos.\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bool;
    }

    public Boolean eliminarReview(String usuario, String pelicula){
        Boolean boleano= false;
        Connection connection;
        try{
            try {
                Class.forName("org.postgresql.Driver");
            }catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(url, user, password);

            if(connection!=null){
                String sql = "DELETE FROM REVIEWS WHERE user_name=? AND movie=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, usuario);
                st.setString(2,pelicula);
                int contador = st.executeUpdate();
                if(contador==1){
                    System.out.println("Se ha borrado la review del usuario "+ usuario);
                    boleano = true;
                }
                st.close();
                connection.close();
            }else{
                System.out.println("No se ha podido conectar a la base de datos.\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return boleano;
    }

    public Boolean actualizarReview(String texto,String usuario,String pelicula){
        Boolean bool = false;
        Connection connection;
        try{
            try {
                Class.forName("org.postgresql.Driver");
            }catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(url, user, password);

            if(connection!=null){
                String sql = "UPDATE REVIEWS SET review=? AND fec=? WHERE user_name=? AND movie=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, texto);
                st.setString(2, usuario);
                st.setString(3, pelicula);
                st.setDate(4, (Date) date); //Casteamos de java.util.date a java.sql.date
                int contador = st.executeUpdate();
                if(contador==1){
                    System.out.println("Se ha actualizado la review del usuario: "+ usuario);
                    bool = true;
                }
                st.close();
                connection.close();
            }else{
                System.out.println("No se ha podido conectar a la base de datos.\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return bool;
    }

    public Boolean existeReview(String name,String movie) {
        Connection connection  = null;
        Boolean boleano = false;

        try{
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(url, user, password);

            if(connection!=null){
                String sql = "SELECT * FROM REVIEWS WHERE user_name=? AND movie = ?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, name);
                st.setString(2,movie);
                ResultSet rs = st.executeQuery();
                if(rs.next()){
                    System.out.println("Se ha encontrado la review del usuario"+name+"en la pelicula"+movie);
                    boleano = true;
                }
                rs.close();
                st.close();
                connection.close();
            }else
                System.out.println("No se ha podido conectar a la base de datos.\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        return boleano;
    }

    public List<Review> mostrarReviews(String movie) {
        Connection connection = null;
        List<Review> lista_rev = new ArrayList<>();
        Review review_aux=null;
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                String sql = "select * from reviews where movie=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, movie);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    review_aux = new Review();
                    review_aux.setUsuario(rs.getString(2));
                    review_aux.setTitulo(rs.getString(3));
                    review_aux.setReview(rs.getString(4));
                    review_aux.setFecha(rs.getDate(5));
                    lista_rev.add(review_aux);
                    //Review review_aux = new Review(rs.getString("review"), rs.getString("user"), rs.getString("movie"));
                }
                rs.close();
                st.close();
                connection.close();
            } else {
                System.out.println("No se ha podido conectar con la base de datos.\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista_rev;
    }


    public boolean eliminarReviewsPelicula(String title){
        Connection connection = null;
        Boolean resultado=false;
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                String sql = "delete from reviews where movie=?";
                PreparedStatement st = connection.prepareStatement(sql);
                st.setString(1, title);

                if (st.executeUpdate() == 1) {
                    resultado = true;
                }
                st.close();
                connection.close();
            } else {
                System.out.println("No se ha podido conectar con la base de datos.\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

}
