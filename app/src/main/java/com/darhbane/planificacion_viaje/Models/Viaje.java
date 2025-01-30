package com.darhbane.planificacion_viaje.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Viaje")
public class Viaje {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombreViaje;
    private String destino;
    private String fechaInicio;
    private String fechaFin;
    private int presupuesto;
    private boolean alojamientoReservado;
    private boolean transporteIncluido;
    private String tipoViaje;

    // Constructor
    public Viaje(String nombreViaje, String destino, String fechaInicio, String fechaFin,
                 int presupuesto, boolean alojamientoReservado, boolean transporteIncluido, String tipoViaje) {
        this.nombreViaje = nombreViaje;
        this.destino = destino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.alojamientoReservado = alojamientoReservado;
        this.transporteIncluido = transporteIncluido;
        this.tipoViaje = tipoViaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreViaje() {
        return nombreViaje;
    }

    public void setNombreViaje(String nombreViaje) {
        this.nombreViaje = nombreViaje;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public boolean isAlojamientoReservado() {
        return alojamientoReservado;
    }

    public void setAlojamientoReservado(boolean alojamientoReservado) {
        this.alojamientoReservado = alojamientoReservado;
    }

    public boolean isTransporteIncluido() {
        return transporteIncluido;
    }

    public void setTransporteIncluido(boolean transporteIncluido) {
        this.transporteIncluido = transporteIncluido;
    }

    public String getTipoViaje() {
        return tipoViaje;
    }

    public void setTipoViaje(String tipoViaje) {
        this.tipoViaje = tipoViaje;
    }
}
