package tn.isim.suivisport.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Entité enfant : un exercice appartient à une séance.
 * Suppression de la séance => suppression en cascade de ses exercices.
 */
@Entity(
        tableName = "exercices",
        foreignKeys = @ForeignKey(
                entity = Seance.class,
                parentColumns = "id",
                childColumns = "seanceId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("seanceId")
)
public class Exercice {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int seanceId;
    private String nom;
    private int series;
    private int repetitions;
    private double chargeKg;
    private int dureeMin;

    public Exercice(int seanceId, String nom, int series, int repetitions,
                    double chargeKg, int dureeMin) {
        this.seanceId = seanceId;
        this.nom = nom;
        this.series = series;
        this.repetitions = repetitions;
        this.chargeKg = chargeKg;
        this.dureeMin = dureeMin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSeanceId() { return seanceId; }
    public void setSeanceId(int seanceId) { this.seanceId = seanceId; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }

    public int getRepetitions() { return repetitions; }
    public void setRepetitions(int repetitions) { this.repetitions = repetitions; }

    public double getChargeKg() { return chargeKg; }
    public void setChargeKg(double chargeKg) { this.chargeKg = chargeKg; }

    public int getDureeMin() { return dureeMin; }
    public void setDureeMin(int dureeMin) { this.dureeMin = dureeMin; }
}
