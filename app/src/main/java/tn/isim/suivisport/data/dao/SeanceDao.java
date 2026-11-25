package tn.isim.suivisport.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tn.isim.suivisport.data.entity.Seance;

@Dao
public interface SeanceDao {

    @Insert
    long insert(Seance seance);

    @Delete
    void delete(Seance seance);

    /** Ordre croissant d'id : la dernière séance créée est le dernier élément. */
    @Query("SELECT * FROM seances ORDER BY id ASC")
    LiveData<List<Seance>> getAll();

    /** Requête paramétrée. */
    @Query("SELECT * FROM seances WHERE id = :seanceId")
    LiveData<Seance> getById(int seanceId);
}
