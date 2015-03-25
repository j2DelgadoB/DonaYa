package com.example.jose.myapplication.models;

/**
 * Created by jose on 27/02/2015.
 */
public class Respuesta {
    String idUser;
    String username;
    String responde;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getResponde() {
        return responde;
    }

    public void setResponde(String responde) {
        this.responde = responde;
    }
}
