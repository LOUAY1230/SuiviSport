package tn.isim.suivisport.util;

import java.util.List;

import tn.isim.suivisport.data.entity.Exercice;

/** Logique métier : volume d'entraînement et durée totale. */
public final class CalculUtil {

    private CalculUtil() { }

    /** Volume d'un exercice = séries x répétitions x charge (kg). */
    public static double volumeExercice(Exercice exercice) {
        return (double) exercice.getSeries() * exercice.getRepetitions() * exercice.getChargeKg();
    }

    /** Volume total d'une liste d'exercices (0 si la liste est vide ou nulle). */
    public static double volumeTotal(List<Exercice> exercices) {
        double total = 0;
        if (exercices == null) {
            return total;
        }
        for (Exercice e : exercices) {
            total += volumeExercice(e);
        }
        return total;
    }

    /** Durée totale en minutes (0 si la liste est vide ou nulle). */
    public static int dureeTotale(List<Exercice> exercices) {
        int total = 0;
        if (exercices == null) {
            return total;
        }
        for (Exercice e : exercices) {
            total += e.getDureeMin();
        }
        return total;
    }

    /** Moyenne protégée contre la division par zéro. */
    public static double moyenne(double total, int count) {
        return count <= 0 ? 0 : total / count;
    }
}
