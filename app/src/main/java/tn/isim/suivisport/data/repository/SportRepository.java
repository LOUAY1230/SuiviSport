package tn.isim.suivisport.data.repository;

import android.app.Application;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import tn.isim.suivisport.data.dao.ExerciceDao;
import tn.isim.suivisport.data.dao.SeanceDao;
import tn.isim.suivisport.data.database.AppDatabase;
import tn.isim.suivisport.data.entity.Exercice;
import tn.isim.suivisport.data.entity.Seance;

/**
 * Repository : seul point d'accès aux DAO.
 * Lectures = LiveData (Room les exécute hors thread principal),
 * écritures = exécutées sur WRITE_EXECUTOR.
 */
public class SportRepository {

    private static final String TAG = "SportRepository";

    private final SeanceDao seanceDao;
    private final ExerciceDao exerciceDao;

    public SportRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        seanceDao = db.seanceDao();
        exerciceDao = db.exerciceDao();
    }

    public LiveData<List<Seance>> getSeances() {
        return seanceDao.getAll();
    }

    public LiveData<Seance> getSeance(int seanceId) {
        return seanceDao.getById(seanceId);
    }

    public LiveData<List<Exercice>> getExercices(int seanceId) {
        return exerciceDao.getBySeance(seanceId);
    }

    public LiveData<List<Exercice>> getAllExercices() {
        return exerciceDao.getAll();
    }

    public void insertSeance(Seance seance) {
        AppDatabase.WRITE_EXECUTOR.execute(() -> seanceDao.insert(seance));
    }

    public void deleteSeance(Seance seance) {
        AppDatabase.WRITE_EXECUTOR.execute(() -> seanceDao.delete(seance));
    }

    public void insertExercice(Exercice exercice) {
        AppDatabase.WRITE_EXECUTOR.execute(() -> {
            try {
                exerciceDao.insert(exercice);
            } catch (SQLiteException e) {
                // Ex. : séance supprimée entre-temps (clé étrangère) -> pas de plantage
                Log.e(TAG, "Insertion de l'exercice impossible", e);
            }
        });
    }

    public void deleteExercice(Exercice exercice) {
        AppDatabase.WRITE_EXECUTOR.execute(() -> exerciceDao.delete(exercice));
    }
}
