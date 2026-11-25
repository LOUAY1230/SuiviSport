package tn.isim.suivisport.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import tn.isim.suivisport.data.entity.Seance;
import tn.isim.suivisport.databinding.ItemSeanceBinding;

/** ListAdapter + DiffUtil ; les clics remontent à l'Activity via l'interface OnSeanceActionListener. */
public class SeanceAdapter extends ListAdapter<Seance, SeanceAdapter.SeanceViewHolder> {

    public interface OnSeanceActionListener {
        void onSeanceClick(Seance seance);

        void onSeanceDelete(Seance seance);
    }

    private static final DiffUtil.ItemCallback<Seance> DIFF = new DiffUtil.ItemCallback<Seance>() {
        @Override
        public boolean areItemsTheSame(@NonNull Seance oldItem, @NonNull Seance newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Seance oldItem, @NonNull Seance newItem) {
            return Objects.equals(oldItem.getTitre(), newItem.getTitre())
                    && Objects.equals(oldItem.getDate(), newItem.getDate())
                    && Objects.equals(oldItem.getNotes(), newItem.getNotes());
        }
    };

    private final OnSeanceActionListener listener;

    public SeanceAdapter(OnSeanceActionListener listener) {
        super(DIFF);
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSeanceBinding binding = ItemSeanceBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new SeanceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SeanceViewHolder holder, int position) {
        final Seance seance = getItem(position);
        holder.binding.tvTitre.setText(seance.getTitre());
        holder.binding.tvDate.setText(seance.getDate());
        holder.binding.getRoot().setOnClickListener(v -> listener.onSeanceClick(seance));
        holder.binding.btnDelete.setOnClickListener(v -> listener.onSeanceDelete(seance));
    }

    static class SeanceViewHolder extends RecyclerView.ViewHolder {
        final ItemSeanceBinding binding;

        SeanceViewHolder(@NonNull ItemSeanceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
