package com.darhbane.planificacion_viaje.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.darhbane.planificacion_viaje.R;


public class ConfiguracionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Si quieres usar un layout, por ejemplo activity_configuracion.xml
        setContentView(R.layout.activity_configuracion);

        // Inserta el fragmento de preferencias
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_config, new SettingsFragment())
                    .commit();
        }
    }
}
