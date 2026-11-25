package tn.isim.suivisport.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Entité parent : une séance de sport (relation 1-N avec Exercice). */
@Entity(tableName = "seances")
public class Seance {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titre;
    private String date;
    private String notes;

    public Seance(String titre, String date, String notes) {
        this.titre = titre;
        this.date = date;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
