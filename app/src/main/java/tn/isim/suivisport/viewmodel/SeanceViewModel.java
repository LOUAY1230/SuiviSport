package tn.isim.suivisport.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tn.isim.suivisport.data.entity.Seance;
import tn.isim.suivisport.data.repository.SportRepository;

public class SeanceViewModel extends AndroidViewModel {

    private final SportRepository repository;
    private final LiveData<List<Seance>> seances;

    public SeanceViewModel(@NonNull Application application) {
        super(application);
        repository = new SportRepository(application);
        seances = repository.getSeances();
    }

    public LiveData<List<Seance>> getSeances() {
        return seances;
    }

    public LiveData<Seance> getSeance(int seanceId) {
        return repository.getSeance(seanceId);
    }

    public void insertSeance(Seance seance) {
        repository.insertSeance(seance);
    }

    public void deleteSeance(Seance seance) {
        repository.deleteSeance(seance);
    }
}
