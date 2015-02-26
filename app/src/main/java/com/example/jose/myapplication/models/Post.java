package com.example.jose.myapplication.models;

import java.util.ArrayList;

/**
 * Created by jose on 26/02/2015.
 */
public class Post {
    protected String solicita;
    protected ArrayList<String> respuesta;

    public String getSolicita() {
        return solicita;
    }

    public void setSolicita(String solicita) {
        this.solicita = solicita;
    }

    public ArrayList<String> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(ArrayList<String> respuesta) {
        this.respuesta = respuesta;
    }
}
