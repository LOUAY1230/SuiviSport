package tn.isim.suivisport.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import tn.isim.suivisport.R;
import tn.isim.suivisport.data.entity.Exercice;
import tn.isim.suivisport.databinding.ItemExerciceBinding;
import tn.isim.suivisport.util.FormatUtil;

/** ListAdapter + DiffUtil pour les exercices ; suppression remontée par interface. */
public class ExerciceAdapter extends ListAdapter<Exercice, ExerciceAdapter.ExerciceViewHolder> {

    public interface OnExerciceActionListener {
        void onExerciceDelete(Exercice exercice);
    }

    private static final DiffUtil.ItemCallback<Exercice> DIFF = new DiffUtil.ItemCallback<Exercice>() {
        @Override
        public boolean areItemsTheSame(@NonNull Exercice oldItem, @NonNull Exercice newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercice oldItem, @NonNull Exercice newItem) {
            return Objects.equals(oldItem.getNom(), newItem.getNom())
                    && oldItem.getSeries() == newItem.getSeries()
                    && oldItem.getRepetitions() == newItem.getRepetitions()
                    && Double.compare(oldItem.getChargeKg(), newItem.getChargeKg()) == 0
                    && oldItem.getDureeMin() == newItem.getDureeMin();
        }
    };

    private final OnExerciceActionListener listener;

    public ExerciceAdapter(OnExerciceActionListener listener) {
        super(DIFF);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExerciceBinding binding = ItemExerciceBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ExerciceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciceViewHolder holder, int position) {
        final Exercice exercice = getItem(position);
        Context context = holder.binding.getRoot().getContext();
        holder.binding.tvNom.setText(exercice.getNom());
        holder.binding.tvDetails.setText(context.getString(
                R.string.exercice_details,
                exercice.getSeries(),
                exercice.getRepetitions(),
                FormatUtil.formatNumber(exercice.getChargeKg()),
                FormatUtil.formatNumber(exercice.getDureeMin())));
        holder.binding.btnDelete.setOnClickListener(v -> listener.onExerciceDelete(exercice));
    }

    static class ExerciceViewHolder extends RecyclerView.ViewHolder {
        final ItemExerciceBinding binding;

        ExerciceViewHolder(@NonNull ItemExerciceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
