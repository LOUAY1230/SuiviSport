package tn.isim.suivisport.util;

/** Validation des saisies : renvoie null si la valeur est vide, invalide ou hors intervalle. */
public final class ValidationUtil {

    private ValidationUtil() { }

    public static Integer parseIntInRange(String raw, int min, int max) {
        if (raw == null) {
            return null;
        }
        String s = raw.trim();
        if (s.isEmpty()) {
            return null;
        }
        try {
            int value = Integer.parseInt(s);
            return (value >= min && value <= max) ? value : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseDoubleInRange(String raw, double min, double max) {
        if (raw == null) {
            return null;
        }
        String s = raw.trim().replace(',', '.');
        if (s.isEmpty()) {
            return null;
        }
        try {
            double value = Double.parseDouble(s);
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                return null;
            }
            return (value >= min && value <= max) ? value : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
