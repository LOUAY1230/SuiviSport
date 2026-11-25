package tn.isim.suivisport.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import tn.isim.suivisport.R;
import tn.isim.suivisport.data.entity.Seance;
import tn.isim.suivisport.databinding.ActivitySeanceFormBinding;
import tn.isim.suivisport.util.Constants;
import tn.isim.suivisport.util.DateUtil;
import tn.isim.suivisport.viewmodel.SeanceViewModel;

/** Formulaire de création d'une séance (titre, date, notes). */
public class SeanceFormActivity extends AppCompatActivity {

    private ActivitySeanceFormBinding binding;
    private SeanceViewModel seanceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeanceFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        seanceViewModel = new ViewModelProvider(this).get(SeanceViewModel.class);

        // Date du jour par défaut ; après rotation le texte est restauré par le système
        if (savedInstanceState == null) {
            binding.etDate.setText(DateUtil.today());
        }
        binding.etDate.setOnClickListener(v -> showDatePicker());
        binding.btnSave.setOnClickListener(v -> saveSeance());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, day) -> binding.etDate.setText(DateUtil.format(year, month, day)),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveSeance() {
        binding.tilTitre.setError(null);

        String titre = text(binding.etTitre);
        String date = text(binding.etDate);
        String notes = text(binding.etNotes);

        if (titre.isEmpty()) {
            binding.tilTitre.setError(getString(R.string.error_titre_required));
            return;
        }
        if (titre.length() > Constants.MAX_TITRE_LENGTH) {
            binding.tilTitre.setError(getString(R.string.error_titre_too_long));
            return;
        }
        if (date.isEmpty()) {
            date = DateUtil.today();
        }

        seanceViewModel.insertSeance(new Seance(titre, date, notes));
        Toast.makeText(this, R.string.msg_seance_saved, Toast.LENGTH_SHORT).show();
        finish(); // retour à l'accueil, la liste se met à jour via LiveData
    }

    private String text(TextInputEditText field) {
        return field.getText() == null ? "" : field.getText().toString().trim();
    }
}
