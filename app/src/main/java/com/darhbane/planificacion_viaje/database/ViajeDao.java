package com.darhbane.planificacion_viaje.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.darhbane.planificacion_viaje.Models.Viaje;

import java.util.List;


@Dao
public interface ViajeDao {

    @Insert
    void insertarViaje(Viaje viaje);


    @Query("SELECT * FROM Viaje")
    List<Viaje> obtenerTodosLosViajes();

    @Query("SELECT * FROM Viaje WHERE id = :id LIMIT 1")
    Viaje obtenerViajePorId(int id);

    @Query("SELECT COUNT(*) FROM Viaje")
    int contarViajes();
}
