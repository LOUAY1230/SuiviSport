package tn.isim.suivisport.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tn.isim.suivisport.data.entity.Exercice;

/** Jeu de test du calcul métier et de la validation des saisies. */
public class CalculUtilTest {

    @Test
    public void volumeTotal_additionneLesExercices() {
        List<Exercice> list = Arrays.asList(
                new Exercice(1, "Développé couché", 4, 10, 60, 15),  // 2400
                new Exercice(1, "Écarté", 3, 12, 20, 10));           // 720
        assertEquals(3120.0, CalculUtil.volumeTotal(list), 0.001);
        assertEquals(25, CalculUtil.dureeTotale(list));
    }

    @Test
    public void listeVideOuNulle_donneZero() {
        assertEquals(0.0, CalculUtil.volumeTotal(new ArrayList<>()), 0.001);
        assertEquals(0.0, CalculUtil.volumeTotal(null), 0.001);
        assertEquals(0, CalculUtil.dureeTotale(null));
    }

    @Test
    public void moyenne_pasDeDivisionParZero() {
        assertEquals(0.0, CalculUtil.moyenne(100, 0), 0.001);
        assertEquals(50.0, CalculUtil.moyenne(100, 2), 0.001);
    }

    @Test
    public void validation_refuseLesValeursInvalides() {
        assertNull(ValidationUtil.parseIntInRange("", 1, 100));
        assertNull(ValidationUtil.parseIntInRange("abc", 1, 100));
        assertNull(ValidationUtil.parseIntInRange("0", 1, 100));
        assertNull(ValidationUtil.parseIntInRange("99999999999", 1, 100));
        assertEquals(Integer.valueOf(5), ValidationUtil.parseIntInRange(" 5 ", 1, 100));
        assertEquals(Double.valueOf(12.5), ValidationUtil.parseDoubleInRange("12,5", 0, 1000));
        assertNull(ValidationUtil.parseDoubleInRange("-3", 0, 1000));
        assertNull(ValidationUtil.parseDoubleInRange("NaN", 0, 1000));
    }
}
