package tn.isim.suivisport.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import tn.isim.suivisport.R;
import tn.isim.suivisport.databinding.ActivitySettingsBinding;
import tn.isim.suivisport.util.Constants;
import tn.isim.suivisport.util.PrefsManager;

/** Réglages mémorisés avec SharedPreferences : nom d'utilisateur, thème sombre, ordre de tri. */
public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private PrefsManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new PrefsManager(this);

        if (savedInstanceState == null) {
            binding.etUsername.setText(prefs.getUsername());
        }
        binding.switchDarkMode.setChecked(prefs.isDarkMode());
        binding.switchSortRecent.setChecked(prefs.isSortRecentFirst());

        binding.switchDarkMode.setOnCheckedChangeListener((button, checked) -> {
            prefs.setDarkMode(checked);
            AppCompatDelegate.setDefaultNightMode(checked
                    ? AppCompatDelegate.MODE_NIGHT_YES
                    : AppCompatDelegate.MODE_NIGHT_NO);
        });
        binding.switchSortRecent.setOnCheckedChangeListener((button, checked) ->
                prefs.setSortRecentFirst(checked));

        binding.btnSaveUsername.setOnClickListener(v -> saveUsername());
    }

    private void saveUsername() {
        binding.tilUsername.setError(null);
        String name = binding.etUsername.getText() == null
                ? "" : binding.etUsername.getText().toString().trim();
        if (name.length() > Constants.MAX_USERNAME_LENGTH) {
            binding.tilUsername.setError(getString(R.string.error_username_too_long));
            return;
        }
        prefs.setUsername(name); // vide autorisé : le nom est facultatif
        Toast.makeText(this, R.string.msg_settings_saved, Toast.LENGTH_SHORT).show();
    }
}
