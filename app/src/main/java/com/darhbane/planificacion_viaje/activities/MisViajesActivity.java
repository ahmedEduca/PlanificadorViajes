package com.darhbane.planificacion_viaje.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darhbane.planificacion_viaje.Models.Viaje;
import com.darhbane.planificacion_viaje.R;
import com.darhbane.planificacion_viaje.adapters.MisViajesAdapter;
import com.darhbane.planificacion_viaje.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class MisViajesActivity extends AppCompatActivity {

    private RecyclerView rvMisViajes;
    private MisViajesAdapter adapter;
    private List<Viaje> listaViajes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_viajes);

        initViews();
        initRecyclerView();
        cargarViajes(); // Cargar los viajes directamente
    }

    private void initViews() {
        rvMisViajes = findViewById(R.id.rvMisViajes);
    }

    private void initRecyclerView() {
        rvMisViajes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MisViajesAdapter(listaViajes);
        rvMisViajes.setAdapter(adapter);
    }

    private void cargarViajes() {
        // Carga los viajes de la base de datos directamente en el hilo principal
        List<Viaje> viajesBD = AppDatabase
                .getDatabase(getApplicationContext())
                .viajeDao()
                .obtenerTodosLosViajes();

        // Actualizar la lista y notificar al Adapter
        listaViajes.clear();
        listaViajes.addAll(viajesBD);
        adapter.notifyDataSetChanged();
    }
}
