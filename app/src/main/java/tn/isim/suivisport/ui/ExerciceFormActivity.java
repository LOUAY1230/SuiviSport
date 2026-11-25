package tn.isim.suivisport.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import tn.isim.suivisport.R;
import tn.isim.suivisport.data.entity.Exercice;
import tn.isim.suivisport.databinding.ActivityExerciceFormBinding;
import tn.isim.suivisport.util.Constants;
import tn.isim.suivisport.util.ValidationUtil;
import tn.isim.suivisport.viewmodel.ExerciceViewModel;

/** Formulaire d'ajout d'un exercice à la séance reçue en extra. */
public class ExerciceFormActivity extends AppCompatActivity {

    private ActivityExerciceFormBinding binding;
    private ExerciceViewModel exerciceViewModel;
    private int seanceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExerciceFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        seanceId = getIntent().getIntExtra(Constants.EXTRA_SEANCE_ID, -1);
        if (seanceId <= 0) { // extra absent : on évite d'insérer un exercice orphelin
            Toast.makeText(this, R.string.msg_invalid_seance, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);
        binding.btnSave.setOnClickListener(v -> saveExercice());
    }

    private void saveExercice() {
        binding.tilNom.setError(null);
        binding.tilSeries.setError(null);
        binding.tilReps.setError(null);
        binding.tilCharge.setError(null);
        binding.tilDuree.setError(null);

        boolean valid = true;

        String nom = text(binding.etNom);
        if (nom.isEmpty()) {
            binding.tilNom.setError(getString(R.string.error_nom_required));
            valid = false;
        } else if (nom.length() > Constants.MAX_NOM_LENGTH) {
            binding.tilNom.setError(getString(R.string.error_nom_too_long));
            valid = false;
        }

        Integer series = ValidationUtil.parseIntInRange(text(binding.etSeries), 1, 100);
        if (series == null) {
            binding.tilSeries.setError(getString(R.string.error_series_invalid));
            valid = false;
        }

        Integer reps = ValidationUtil.parseIntInRange(text(binding.etReps), 1, 1000);
        if (reps == null) {
            binding.tilReps.setError(getString(R.string.error_reps_invalid));
            valid = false;
        }

        // Champs facultatifs : vide = 0, mais une valeur saisie doit être valide
        double charge = 0;
        String chargeRaw = text(binding.etCharge);
        if (!chargeRaw.isEmpty()) {
            Double parsed = ValidationUtil.parseDoubleInRange(chargeRaw, 0, 1000);
            if (parsed == null) {
                binding.tilCharge.setError(getString(R.string.error_charge_invalid));
                valid = false;
            } else {
                charge = parsed;
            }
        }

        int duree = 0;
        String dureeRaw = text(binding.etDuree);
        if (!dureeRaw.isEmpty()) {
            Integer parsed = ValidationUtil.parseIntInRange(dureeRaw, 0, 600);
            if (parsed == null) {
                binding.tilDuree.setError(getString(R.string.error_duree_invalid));
                valid = false;
            } else {
                duree = parsed;
            }
        }

        if (!valid) {
            return;
        }

        exerciceViewModel.insertExercice(new Exercice(seanceId, nom, series, reps, charge, duree));
        Toast.makeText(this, R.string.msg_exercice_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    private String text(TextInputEditText field) {
        return field.getText() == null ? "" : field.getText().toString().trim();
    }
}
