package tn.isim.suivisport.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/** Formatage des nombres affichés (2 décimales maximum, sans zéros inutiles). */
public final class FormatUtil {

    private FormatUtil() { }

    public static String formatNumber(double value) {
        DecimalFormat format = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.getDefault()));
        return format.format(value);
    }
}
