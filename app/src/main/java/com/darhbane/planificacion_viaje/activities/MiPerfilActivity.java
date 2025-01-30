package com.darhbane.planificacion_viaje.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.darhbane.planificacion_viaje.Models.Usuario;
import com.darhbane.planificacion_viaje.R;
import com.darhbane.planificacion_viaje.database.AppDatabase;
import com.darhbane.planificacion_viaje.database.UsuarioDao;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MiPerfilActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Constantes para permisos y selección de imágenes
    private static final int REQUEST_IMAGE_PICK = 100;
    private static final int REQUEST_LOCATION_PERMISSION = 101;

    // Componentes de la UI
    private ImageView ivFotoPerfil;
    private EditText etNombreUsuario, etContrasena;
    private Button btnGuardarPerfil;

    // Datos del usuario
    private UsuarioDao usuarioDao;
    private Usuario usuarioLogueado;
    private Uri fotoSeleccionada;

    // Google Maps y ubicación
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        // Inicializar componentes de UI
        initViews();

        // Inicializar base de datos y cargar datos del usuario
        usuarioDao = AppDatabase.getDatabase(this).usuarioDao();
        cargarUsuarioLogueado();

        // Configurar eventos de botones
        initListeners();

        // Configurar mapa
        configurarMapa();

        // Inicializar cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void initViews() {
        ivFotoPerfil = findViewById(R.id.ivFotoPerfil);
        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnGuardarPerfil = findViewById(R.id.btnGuardarPerfil);
    }

    /**
     * Carga la información del usuario en la UI.
     */
    private void cargarUsuarioLogueado() {
        usuarioLogueado = usuarioDao.obtenerUsuarioPorId(1);

        if (usuarioLogueado != null) {
            etNombreUsuario.setText(usuarioLogueado.getUsername());
            etContrasena.setText(usuarioLogueado.getPassword());

            if (usuarioLogueado.getFotoPerfil() != null) {
                ivFotoPerfil.setImageURI(Uri.parse(usuarioLogueado.getFotoPerfil()));
            }
        }
    }

    private void initListeners() {
        ivFotoPerfil.setOnClickListener(view -> seleccionarFoto());
        btnGuardarPerfil.setOnClickListener(view -> guardarPerfil());
    }

    /**
     * Abre la galería del dispositivo para seleccionar una foto de perfil.
     */
    private void seleccionarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                fotoSeleccionada = selectedImageUri;
                ivFotoPerfil.setImageURI(fotoSeleccionada);
            }
        }
    }

    /**
     * Guarda los cambios del perfil del usuario en la base de datos.
     */
    private void guardarPerfil() {
        if (usuarioLogueado == null) {
            Toast.makeText(this, "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        usuarioLogueado.setUsername(etNombreUsuario.getText().toString());
        usuarioLogueado.setPassword(etContrasena.getText().toString());

        if (fotoSeleccionada != null) {
            usuarioLogueado.setFotoPerfil(fotoSeleccionada.toString());
        }

        usuarioDao.updateUsuario(usuarioLogueado);
        Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
    }

    /**
     * Configura el mapa en la UI.
     */
    private void configurarMapa() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        verificarPermisosUbicacion();
    }

    /**
     * Verifica si se tienen permisos de ubicación, y en caso contrario los solicita.
     */
    private void verificarPermisosUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mostrarUbicacionActual();
        }
    }

    /**
     * Muestra la ubicación actual del usuario en el mapa.
     */
    private void mostrarUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 15f));

                    mMap.addMarker(new MarkerOptions()
                            .position(ubicacionActual)
                            .title("Tu ubicación actual"));
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mostrarUbicacionActual();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
