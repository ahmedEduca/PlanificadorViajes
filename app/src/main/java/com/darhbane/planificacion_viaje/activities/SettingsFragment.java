package com.darhbane.planificacion_viaje.activities;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.darhbane.planificacion_viaje.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Cargar el archivo preferences.xml
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
