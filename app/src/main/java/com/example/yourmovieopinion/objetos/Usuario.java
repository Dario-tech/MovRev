package com.example.yourmovieopinion.objetos;

public class Usuario {
    int id;
    String user_name;
    String email;
    String pass;

    public Usuario (int id,String user,String email, String pass){
        this.id = id;
        this.user_name = user;
        this.email = email;
        this.pass = pass;
    }
    public Usuario (){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

