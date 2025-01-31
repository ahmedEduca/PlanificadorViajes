package com.darhbane.planificacion_viaje.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.darhbane.planificacion_viaje.Models.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    void insertarUsuario(Usuario usuario);

    @Delete
    void eliminarUsuario(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE id = :usuarioId LIMIT 1")
    Usuario getUsuarioLogueado(int usuarioId);

    @Update
    void updateUsuario(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE username = :username AND password = :password LIMIT 1")
    Usuario login(String username, String password);

    @Query("SELECT * FROM usuario WHERE id = :userId LIMIT 1")
    Usuario obtenerUsuarioPorId(int userId);




}
