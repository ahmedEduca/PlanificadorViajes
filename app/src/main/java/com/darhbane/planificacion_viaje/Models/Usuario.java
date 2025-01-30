package com.darhbane.planificacion_viaje.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;
    private String fotoPerfil;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.fotoPerfil = fotoPerfil;
    }



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String imagenPerfil) {
        this.fotoPerfil = imagenPerfil;
    }
}
