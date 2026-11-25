package tn.isim.suivisport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import tn.isim.suivisport.databinding.ActivitySplashBinding;

/** Écran de démarrage : affiche le logo puis ouvre l'accueil et se ferme (finish()). */
public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_MS = 1500;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable openHome = () -> {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish(); // le splash ne doit pas rester dans la pile (retour arrière)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(openHome, DELAY_MS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(openHome); // évite une fuite / une ouverture après rotation
    }
}
