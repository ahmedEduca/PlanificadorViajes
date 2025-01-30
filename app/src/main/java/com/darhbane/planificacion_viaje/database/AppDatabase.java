package com.darhbane.planificacion_viaje.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.darhbane.planificacion_viaje.Models.Usuario;
import com.darhbane.planificacion_viaje.Models.Viaje;

@Database(entities = {Usuario.class, Viaje.class}, version = 3, exportSchema = false) // ⚠️ Asegúrate de subir la versión
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
    public abstract ViajeDao viajeDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "darhbane_agency_db"
                            )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public Usuario getUsuarioLogueado(int usuarioId) {
        return usuarioDao().getUsuarioLogueado(usuarioId);
    }
}
