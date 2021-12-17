package kg.geektech.taskapp37.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kg.geektech.taskapp37.R;
import kg.geektech.taskapp37.databinding.ItemBoardBinding;
import kg.geektech.taskapp37.interfaces.OnBoardStartClickListener;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private ItemBoardBinding binding;
    private OnBoardStartClickListener onBoardStartClickListener;
    private final String[] titles = {"Идея", "Навигация","Адрес"};
    private final String[] descriptions = {"Идеи для сторис", "Навигация для сторис","Как нас найти"};
    private final int[] images = {R.drawable.img, R.drawable.img_2, R.drawable.img_1};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBoardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setOnBoardStartClickListener(OnBoardStartClickListener onBoardStartClickListener) {
        this.onBoardStartClickListener = onBoardStartClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemBoardBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.btnStart.setOnClickListener(v -> onBoardStartClickListener.OnStartClick());
        }

        public void bind(int position) {
            binding.imageView.setImageResource(images[position]);
            binding.textTitle.setText(titles[position]);
            binding.textDesc.setText(descriptions[position]);

            if (position==titles.length-1){
                binding.btnStart.setVisibility(View.VISIBLE);
            }else {
                binding.btnStart.setVisibility(View.INVISIBLE);
            }
        }
    }
}
