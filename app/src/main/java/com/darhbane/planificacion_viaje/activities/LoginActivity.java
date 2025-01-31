package com.darhbane.planificacion_viaje.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.darhbane.planificacion_viaje.Models.Usuario;
import com.darhbane.planificacion_viaje.R;
import com.darhbane.planificacion_viaje.database.AppDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasena;
    private CheckBox cbRecordar;
    private Button btnLogin;

    private AppDatabase db;

    private SharedPreferences sharedPreferences;

    private MediaPlayer mediaPlayer;

    private static final String PREF_NAME = "login_preferences";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IS_LOGGED_IN = "IsLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initDatabase();
        initViews();

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        verificarSesionActiva();

        initListeners();
        initMusic();


    }

    private void initMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.login_audio);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void initDatabase() {
       db = AppDatabase.getDatabase(this);
       db.usuarioDao().insertarUsuario(new Usuario("admin", "admin"));
    }

    private void initViews() {
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        cbRecordar = findViewById(R.id.cbRecordar);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void initListeners() {
        btnLogin.setOnClickListener(view -> {
            String username = etUsuario.getText().toString().trim();
            String password = etContrasena.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            }else {
                Usuario usuario = db.usuarioDao().login(username,password);
                if(usuario!= null) {
                    if (cbRecordar.isChecked()) {
                        guardarSesion(username);
                    }

                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    redirigirADashboard();
                }else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verificarSesionActiva() {
        Boolean isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false);

        if (isLoggedIn) {
            String username = sharedPreferences.getString(KEY_USERNAME,"");
            Toast.makeText(this, "Bienvenido de nuevo", Toast.LENGTH_SHORT).show();

            redirigirADashboard();
        }
    }

    private void guardarSesion(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    private void redirigirADashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}