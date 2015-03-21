package com.example.jose.myapplication.models;

/**
 * Created by jose on 20/03/2015.
 */
public class Perfil {
    String nombres;
    String apellidos;
    String tipoSangre;
    String email;
    String telefono;
    String cuentaFace;
    String cuentaTwitt;
    String cuentaGoogle;
    String fondo;
    String foto;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuentaFace() {
        return cuentaFace;
    }

    public void setCuentaFace(String cuentaFace) {
        this.cuentaFace = cuentaFace;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCuentaTwitt() {
        return cuentaTwitt;
    }

    public void setCuentaTwitt(String cuentaTwitt) {
        this.cuentaTwitt = cuentaTwitt;
    }

    public String getCuentaGoogle() {
        return cuentaGoogle;
    }

    public void setCuentaGoogle(String cuentaGoogle) {
        this.cuentaGoogle = cuentaGoogle;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
