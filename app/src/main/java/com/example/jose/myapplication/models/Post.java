package com.example.jose.myapplication.models;

import java.util.ArrayList;

/**
 * Created by jose on 26/02/2015.
 */
public class Post {
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
}
