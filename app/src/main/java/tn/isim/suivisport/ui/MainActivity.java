package tn.isim.suivisport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tn.isim.suivisport.R;
import tn.isim.suivisport.data.entity.Seance;
import tn.isim.suivisport.databinding.ActivityMainBinding;
import tn.isim.suivisport.util.Constants;
import tn.isim.suivisport.util.PrefsManager;
import tn.isim.suivisport.viewmodel.SeanceViewModel;

/** Écran d'accueil : liste des séances, accès aux statistiques et aux réglages. */
public class MainActivity extends AppCompatActivity implements SeanceAdapter.OnSeanceActionListener {

    private ActivityMainBinding binding;
    private SeanceViewModel seanceViewModel;
    private SeanceAdapter adapter;
    private PrefsManager prefs;
    private List<Seance> lastSeances = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = new PrefsManager(this);

        adapter = new SeanceAdapter(this);
        binding.rvSeances.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSeances.setAdapter(adapter);

        seanceViewModel = new ViewModelProvider(this).get(SeanceViewModel.class);
        seanceViewModel.getSeances().observe(this, seances -> {
            lastSeances = seances != null ? seances : new ArrayList<>();
            refreshList();
        });

        binding.btnAddSeance.setOnClickListener(v ->
                startActivity(new Intent(this, SeanceFormActivity.class)));
        binding.btnStats.setOnClickListener(v ->
                startActivity(new Intent(this, StatsActivity.class)));
        binding.btnSettings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Les réglages (nom, tri) ont pu changer dans l'écran Réglages
        String name = prefs.getUsername();
        binding.tvGreeting.setText(name.isEmpty()
                ? getString(R.string.main_greeting_anonymous)
                : getString(R.string.main_greeting_named, name));
        refreshList();
    }

    /** Applique l'ordre de tri choisi dans les réglages puis met à jour la liste (DiffUtil). */
    private void refreshList() {
        List<Seance> sorted = new ArrayList<>(lastSeances);
        if (prefs.isSortRecentFirst()) {
            Collections.reverse(sorted); // le DAO renvoie l'ordre croissant d'id
        }
        adapter.submitList(sorted);
        binding.tvEmpty.setVisibility(sorted.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSeanceClick(Seance seance) {
        // Intent explicite avec extras
        Intent intent = new Intent(this, SeanceDetailActivity.class);
        intent.putExtra(Constants.EXTRA_SEANCE_ID, seance.getId());
        intent.putExtra(Constants.EXTRA_SEANCE_TITRE, seance.getTitre());
        startActivity(intent);
    }

    @Override
    public void onSeanceDelete(Seance seance) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.confirm_delete_seance_title)
                .setMessage(getString(R.string.confirm_delete_seance_message, seance.getTitre()))
                .setPositiveButton(R.string.btn_delete, (dialog, which) -> seanceViewModel.deleteSeance(seance))
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }
}
