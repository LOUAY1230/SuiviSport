package tn.isim.suivisport.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tn.isim.suivisport.data.entity.Exercice;

@Dao
public interface ExerciceDao {

    @Insert
    long insert(Exercice exercice);

    @Delete
    void delete(Exercice exercice);

    /** Requête paramétrée : exercices d'une séance donnée. */
    @Query("SELECT * FROM exercices WHERE seanceId = :seanceId ORDER BY id ASC")
    LiveData<List<Exercice>> getBySeance(int seanceId);

    @Query("SELECT * FROM exercices")
    LiveData<List<Exercice>> getAll();
}
