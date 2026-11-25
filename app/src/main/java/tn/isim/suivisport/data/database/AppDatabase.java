package tn.isim.suivisport.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tn.isim.suivisport.data.dao.ExerciceDao;
import tn.isim.suivisport.data.dao.SeanceDao;
import tn.isim.suivisport.data.entity.Exercice;
import tn.isim.suivisport.data.entity.Seance;

@Database(entities = {Seance.class, Exercice.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /** Thread d'arrière-plan utilisé pour toutes les écritures (jamais sur le thread principal). */
    public static final ExecutorService WRITE_EXECUTOR = Executors.newFixedThreadPool(2);

    private static volatile AppDatabase instance;

    public abstract SeanceDao seanceDao();

    public abstract ExerciceDao exerciceDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "suivi_sport_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
