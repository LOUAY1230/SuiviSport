package tn.isim.suivisport.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tn.isim.suivisport.data.entity.Exercice;
import tn.isim.suivisport.data.repository.SportRepository;

public class ExerciceViewModel extends AndroidViewModel {

    private final SportRepository repository;

    public ExerciceViewModel(@NonNull Application application) {
        super(application);
        repository = new SportRepository(application);
    }

    public LiveData<List<Exercice>> getExercices(int seanceId) {
        return repository.getExercices(seanceId);
    }

    public LiveData<List<Exercice>> getAllExercices() {
        return repository.getAllExercices();
    }

    public void insertExercice(Exercice exercice) {
        repository.insertExercice(exercice);
    }

    public void deleteExercice(Exercice exercice) {
        repository.deleteExercice(exercice);
    }
}
