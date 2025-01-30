package com.darhbane.planificacion_viaje.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.darhbane.planificacion_viaje.Models.Viaje;
import com.darhbane.planificacion_viaje.R;
import com.darhbane.planificacion_viaje.database.AppDatabase;

import java.util.Calendar;

public class NuevoViajeActivity extends AppCompatActivity {

    private EditText etNombreViaje, etDestino;
    private Button btnFechaInicio, btnFechaFin, btnGuardarViaje;
    private SeekBar sbPresupuesto;
    private CheckBox cbAlojamiento, cbTransporte;
    private RadioGroup rgTipoViaje;
    private TextView tvPresupuesto;

    private String fechaInicio = "";
    private String fechaFin = "";
    private int presupuesto = 0;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_viaje);

        db = AppDatabase.getDatabase(this); // Inicializar base de datos correctamente

        initViews();
        initListeners();
    }

    private void initViews() {
        etNombreViaje = findViewById(R.id.etNombreViaje);
        etDestino = findViewById(R.id.etDestino);
        btnFechaInicio = findViewById(R.id.btnFechaInicio);
        btnFechaFin = findViewById(R.id.btnFechaFin);
        btnGuardarViaje = findViewById(R.id.btnGuardarViaje);
        sbPresupuesto = findViewById(R.id.sbPresupuesto);
        cbAlojamiento = findViewById(R.id.cbAlojamiento);
        cbTransporte = findViewById(R.id.cbTransporte);
        rgTipoViaje = findViewById(R.id.rgTipoViaje);
        tvPresupuesto = findViewById(R.id.tvPresupuesto);

        // Inicializar el valor del presupuesto en el TextView
        tvPresupuesto.setText("$" + sbPresupuesto.getProgress());
    }

    private void initListeners() {
        btnFechaInicio.setOnClickListener(view -> seleccionarFecha(true));
        btnFechaFin.setOnClickListener(view -> seleccionarFecha(false));

        sbPresupuesto.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                presupuesto = i;
                tvPresupuesto.setText("$" + i); // Mostrar valor en tiempo real
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnGuardarViaje.setOnClickListener(view -> guardarViaje());
    }

    private void seleccionarFecha(boolean esFechaInicio) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    if (esFechaInicio) {
                        fechaInicio = fechaSeleccionada;
                        btnFechaInicio.setText(getString(R.string.inicio) + fechaSeleccionada);
                    } else {
                        fechaFin = fechaSeleccionada;
                        btnFechaFin.setText(getString(R.string.fin) + fechaSeleccionada);
                    }
                },
                anio, mes, dia
        );

        datePickerDialog.show();
    }

    private void guardarViaje() {
        String nombreViaje = etNombreViaje.getText().toString().trim();
        String destino = etDestino.getText().toString().trim();
        boolean alojamientoReservado = cbAlojamiento.isChecked();
        boolean transporteIncluido = cbTransporte.isChecked();

        int selectedId = rgTipoViaje.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Selecciona un tipo de viaje", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radioSeleccionado = findViewById(selectedId);
        String tipoViaje = radioSeleccionado.getText().toString();

        if (TextUtils.isEmpty(nombreViaje) || TextUtils.isEmpty(destino) ||
                TextUtils.isEmpty(fechaInicio) || TextUtils.isEmpty(fechaFin)) {
            Toast.makeText(this, "Por favor, completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Viaje nuevoViaje = new Viaje(
                nombreViaje,
                destino,
                fechaInicio,
                fechaFin,
                presupuesto,
                alojamientoReservado,
                transporteIncluido,
                tipoViaje
        );

        // Insertar en la base de datos en un hilo secundario
        new Thread(() -> {
            db.viajeDao().insertarViaje(nuevoViaje);
            runOnUiThread(() -> {
                Toast.makeText(this, "Viaje guardado exitosamente", Toast.LENGTH_SHORT).show();
                finish(); // Cierra la actividad después de guardar
            });
        }).start();
    }
}
