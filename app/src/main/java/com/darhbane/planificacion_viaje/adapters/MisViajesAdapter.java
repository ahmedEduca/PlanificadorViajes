package com.darhbane.planificacion_viaje.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darhbane.planificacion_viaje.R;
import com.darhbane.planificacion_viaje.Models.Viaje;

import java.util.List;

public class MisViajesAdapter extends RecyclerView.Adapter<MisViajesAdapter.MisViajesViewHolder> {

    private List<Viaje> listaViajes;

    public MisViajesAdapter(List<Viaje> listaViajes) {
        this.listaViajes = listaViajes;
    }

    @NonNull
    @Override
    public MisViajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viaje, parent, false);
        return new MisViajesViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MisViajesViewHolder holder, int position) {
        Viaje viaje = listaViajes.get(position);
        holder.bind(viaje);
    }

    @Override
    public int getItemCount() {
        return listaViajes.size();
    }

    static class MisViajesViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombreViaje;
        private TextView tvDestino;
        private TextView tvFechas;

        public MisViajesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreViaje = itemView.findViewById(R.id.tvNombreViaje);
            tvDestino = itemView.findViewById(R.id.tvDestino);
            tvFechas = itemView.findViewById(R.id.tvFechas);
        }

        public void bind(Viaje viaje) {
            tvNombreViaje.setText(viaje.getNombreViaje());
            tvDestino.setText(viaje.getDestino());
            tvFechas.setText(viaje.getFechaInicio() + " - " + viaje.getFechaFin());
        }
    }
}
