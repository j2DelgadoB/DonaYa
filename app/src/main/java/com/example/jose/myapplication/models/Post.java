package com.example.jose.myapplication.models;

import java.util.ArrayList;

/**
 * Created by jose on 26/02/2015.
 */
public class Post {
    protected String id;//Por si un post de la bd xejemp el post3 quedaria posts con ids 4 2 1/entra a postList con 0 1 2
    protected String solicita;
    protected ArrayList<Respuesta> respuesta;

    public String getSolicita() {
        return solicita;
    }

    public void setSolicita(String solicita) {
        this.solicita = solicita;
    }

    public ArrayList<Respuesta> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(ArrayList<Respuesta> respuesta) {
        this.respuesta = respuesta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
