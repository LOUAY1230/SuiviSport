package tn.isim.suivisport.util;

import java.util.Calendar;
import java.util.Locale;

/** Outils de date (format jj/mm/aaaa, chiffres ASCII quelle que soit la langue). */
public final class DateUtil {

    private DateUtil() { }

    /** month est 0-based comme dans DatePickerDialog / Calendar. */
    public static String format(int year, int month, int day) {
        return String.format(Locale.US, "%02d/%02d/%04d", day, month + 1, year);
    }

    public static String today() {
        Calendar c = Calendar.getInstance();
        return format(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }
}
