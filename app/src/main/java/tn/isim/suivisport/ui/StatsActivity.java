package tn.isim.suivisport.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import tn.isim.suivisport.R;
import tn.isim.suivisport.data.entity.Exercice;
import tn.isim.suivisport.data.entity.Seance;
import tn.isim.suivisport.databinding.ActivityStatsBinding;
import tn.isim.suivisport.util.CalculUtil;
import tn.isim.suivisport.util.FormatUtil;
import tn.isim.suivisport.viewmodel.ExerciceViewModel;
import tn.isim.suivisport.viewmodel.SeanceViewModel;

/** Statistiques globales : totaux, volume moyen par séance, dernière séance. */
public class StatsActivity extends AppCompatActivity {

    private ActivityStatsBinding binding;
    private List<Seance> seances = new ArrayList<>();
    private List<Exercice> exercices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SeanceViewModel seanceViewModel = new ViewModelProvider(this).get(SeanceViewModel.class);
        ExerciceViewModel exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);

        seanceViewModel.getSeances().observe(this, list -> {
            seances = list != null ? list : new ArrayList<>();
            refresh();
        });
        exerciceViewModel.getAllExercices().observe(this, list -> {
            exercices = list != null ? list : new ArrayList<>();
            refresh();
        });
    }

    private void refresh() {
        int nbSeances = seances.size();
        double volume = CalculUtil.volumeTotal(exercices);
        int duree = CalculUtil.dureeTotale(exercices);
        double moyenne = CalculUtil.moyenne(volume, nbSeances); // protégé contre nbSeances = 0

        binding.tvTotalSeances.setText(getString(R.string.stats_total_seances, nbSeances));
        binding.tvTotalExercices.setText(getString(R.string.stats_total_exercices, exercices.size()));
        binding.tvTotalVolume.setText(getString(R.string.stats_total_volume, FormatUtil.formatNumber(volume)));
        binding.tvTotalDuree.setText(getString(R.string.stats_total_duree, FormatUtil.formatNumber(duree)));
        binding.tvMoyenneVolume.setText(getString(R.string.stats_moyenne_volume, FormatUtil.formatNumber(moyenne)));

        if (seances.isEmpty()) {
            binding.tvDerniereSeance.setText(R.string.stats_derniere_none);
        } else {
            // Liste triée par id croissant : le dernier élément est la séance la plus récente
            Seance last = seances.get(nbSeances - 1);
            binding.tvDerniereSeance.setText(getString(R.string.stats_derniere, last.getTitre(), last.getDate()));
        }
    }
}
