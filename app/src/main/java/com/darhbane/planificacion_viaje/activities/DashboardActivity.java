package com.darhbane.planificacion_viaje.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.darhbane.planificacion_viaje.Models.Usuario;
import com.darhbane.planificacion_viaje.R;
import com.darhbane.planificacion_viaje.database.AppDatabase;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvBienvenido;
    private TextView tvTotalViajes;
    private CardView cardMisViajes;
    private CardView cardNuevoViaje;
    private CardView cardConfiguracion;
    private CardView cardPerfil;

    private static final String PREF_NAME = "login_preferences";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IS_LOGGED_IN = "IsLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initViews();
        mostrarNombreUsuario();
        cargarTotalViajes();
        initListeners();
    }

    private void initViews() {
        cardMisViajes = findViewById(R.id.cardMisViajes);
        cardNuevoViaje = findViewById(R.id.cardNuevoViaje);
        cardConfiguracion = findViewById(R.id.cardConfiguracion);
        cardPerfil = findViewById(R.id.cardPerfil);
        tvBienvenido = findViewById(R.id.tvBienvenido);
        tvTotalViajes = findViewById(R.id.tvTotalViajes);

    }

    private void mostrarNombreUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);

        if (isLoggedIn) {
            String username = sharedPreferences.getString(KEY_USERNAME, "Usuario");
            tvBienvenido.setText(username);
        } else {
            tvBienvenido.setText("Invitado");
        }
    }


    private void cargarTotalViajes() {
        int total = AppDatabase.getDatabase(this)
                .viajeDao()
                .contarViajes();

        tvTotalViajes.setText(total + " viajes planificados");
    }

    private void initListeners() {
        cardMisViajes.setOnClickListener(view -> {
            abrirMisViajes();
        });

        cardNuevoViaje.setOnClickListener(view -> {
            abrirNuevoViaje();
        });

        cardConfiguracion.setOnClickListener(view -> {
            abrirConfiguracion();
        });

        cardPerfil.setOnClickListener(view -> {
            abrirPerfil();
        });
    }

    private void abrirNuevoViaje() {
        Intent intent = new Intent(this, NuevoViajeActivity.class);
        startActivity(intent);

    }

    private void abrirPerfil() {
        Intent intent = new Intent(this, MiPerfilActivity.class);
        startActivity(intent);
    }

    private void abrirConfiguracion() {
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        startActivity(intent);
    }

    private void abrirMisViajes() {
        Intent intent = new Intent(this, MisViajesActivity.class);
        startActivity(intent);

    }
}