package tn.isim.suivisport.util;

import android.content.Context;
import android.content.SharedPreferences;

/** SharedPreferences : nom d'utilisateur, thème sombre, ordre de tri (toutes les écritures utilisent apply()). */
public class PrefsManager {

    private static final String PREFS_NAME = "suivisport_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_SORT_RECENT = "sort_recent_first";

    private final SharedPreferences prefs;

    public PrefsManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, "");
    }

    public void setUsername(String username) {
        prefs.edit().putString(KEY_USERNAME, username).apply();
    }

    public boolean isDarkMode() {
        return prefs.getBoolean(KEY_DARK_MODE, false);
    }

    public void setDarkMode(boolean enabled) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply();
    }

    public boolean isSortRecentFirst() {
        return prefs.getBoolean(KEY_SORT_RECENT, true);
    }

    public void setSortRecentFirst(boolean enabled) {
        prefs.edit().putBoolean(KEY_SORT_RECENT, enabled).apply();
    }
}
