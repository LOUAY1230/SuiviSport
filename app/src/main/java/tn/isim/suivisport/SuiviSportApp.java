package tn.isim.suivisport;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import tn.isim.suivisport.util.PrefsManager;

/**
 * Classe Application : applique le thème (clair / sombre) mémorisé dans les préférences
 * dès le démarrage du processus.
 */
public class SuiviSportApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefsManager prefs = new PrefsManager(this);
        AppCompatDelegate.setDefaultNightMode(prefs.isDarkMode()
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
