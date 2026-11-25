package tn.isim.suivisport.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import tn.isim.suivisport.R;
import tn.isim.suivisport.data.entity.Exercice;
import tn.isim.suivisport.data.entity.Seance;
import tn.isim.suivisport.databinding.ActivitySeanceDetailBinding;
import tn.isim.suivisport.util.CalculUtil;
import tn.isim.suivisport.util.Constants;
import tn.isim.suivisport.util.FormatUtil;
import tn.isim.suivisport.viewmodel.ExerciceViewModel;
import tn.isim.suivisport.viewmodel.SeanceViewModel;

/** Détail d'une séance : exercices, bilan (volume, durée), partage et e-mail (Intents implicites). */
public class SeanceDetailActivity extends AppCompatActivity implements ExerciceAdapter.OnExerciceActionListener {

    private ActivitySeanceDetailBinding binding;
    private ExerciceViewModel exerciceViewModel;
    private ExerciceAdapter adapter;

    private int seanceId;
    private Seance currentSeance;
    private List<Exercice> currentExercices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeanceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lecture des extras de l'Intent explicite
        seanceId = getIntent().getIntExtra(Constants.EXTRA_SEANCE_ID, -1);
        if (seanceId <= 0) {
            Toast.makeText(this, R.string.msg_invalid_seance, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        String titreExtra = getIntent().getStringExtra(Constants.EXTRA_SEANCE_TITRE);
        if (titreExtra != null) {
            binding.tvTitre.setText(titreExtra);
        }

        adapter = new ExerciceAdapter(this);
        binding.rvExercices.setLayoutManager(new LinearLayoutManager(this));
        binding.rvExercices.setAdapter(adapter);

        SeanceViewModel seanceViewModel = new ViewModelProvider(this).get(SeanceViewModel.class);
        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);

        seanceViewModel.getSeance(seanceId).observe(this, seance -> {
            if (seance == null) { // séance introuvable (ou supprimée)
                Toast.makeText(this, R.string.msg_invalid_seance, Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            currentSeance = seance;
            binding.tvTitre.setText(seance.getTitre());
            binding.tvDate.setText(seance.getDate());
            boolean hasNotes = seance.getNotes() != null && !seance.getNotes().isEmpty();
            binding.tvNotes.setText(seance.getNotes());
            binding.tvNotes.setVisibility(hasNotes ? View.VISIBLE : View.GONE);
        });

        exerciceViewModel.getExercices(seanceId).observe(this, exercices -> {
            currentExercices = exercices != null ? exercices : new ArrayList<>();
            adapter.submitList(new ArrayList<>(currentExercices));
            binding.tvEmpty.setVisibility(currentExercices.isEmpty() ? View.VISIBLE : View.GONE);
            updateTotals();
        });

        binding.btnAddExercice.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExerciceFormActivity.class);
            intent.putExtra(Constants.EXTRA_SEANCE_ID, seanceId);
            startActivity(intent);
        });
        binding.btnShare.setOnClickListener(v -> shareSeance());
        binding.btnEmail.setOnClickListener(v -> emailSeance());
    }

    private void updateTotals() {
        double volume = CalculUtil.volumeTotal(currentExercices);
        int duree = CalculUtil.dureeTotale(currentExercices);
        binding.tvNbExercices.setText(getString(R.string.label_nb_exercices, currentExercices.size()));
        binding.tvVolume.setText(getString(R.string.label_volume, FormatUtil.formatNumber(volume)));
        binding.tvDuree.setText(getString(R.string.label_duree, FormatUtil.formatNumber(duree)));
    }

    private String buildSummary() {
        return getString(R.string.share_seance_text,
                currentSeance.getTitre(),
                currentSeance.getDate(),
                currentExercices.size(),
                FormatUtil.formatNumber(CalculUtil.volumeTotal(currentExercices)),
                FormatUtil.formatNumber(CalculUtil.dureeTotale(currentExercices)));
    }

    /** Intent implicite n°1 : partage du bilan de la séance (ACTION_SEND). */
    private void shareSeance() {
        if (currentSeance == null || currentExercices.isEmpty()) {
            Toast.makeText(this, R.string.msg_nothing_to_share, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, buildSummary());
        try {
            startActivity(Intent.createChooser(send, getString(R.string.share_chooser_title)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.msg_no_share_app, Toast.LENGTH_SHORT).show();
        }
    }

    /** Intent implicite n°2 : e-mail pré-rempli (ACTION_SENDTO mailto:). */
    private void emailSeance() {
        if (currentSeance == null || currentExercices.isEmpty()) {
            Toast.makeText(this, R.string.msg_nothing_to_share, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent mail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        mail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, currentSeance.getTitre()));
        mail.putExtra(Intent.EXTRA_TEXT, buildSummary());
        try {
            startActivity(mail);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.msg_no_email_app, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onExerciceDelete(Exercice exercice) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.confirm_delete_exercice_title)
                .setMessage(getString(R.string.confirm_delete_exercice_message, exercice.getNom()))
                .setPositiveButton(R.string.btn_delete, (dialog, which) -> exerciceViewModel.deleteExercice(exercice))
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }
}
