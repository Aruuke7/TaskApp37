package kg.geektech.taskapp37.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kg.geektech.taskapp37.databinding.RecyclerItemBinding;
import kg.geektech.taskapp37.interfaces.OnItemClickListener;
import kg.geektech.taskapp37.models.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private ArrayList<News> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private RecyclerItemBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));

        if (position % 2==0)
            binding.textView.setBackgroundColor(Color.GREEN);
        else
            binding.textView.setBackgroundColor(Color.YELLOW);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0,news);
        notifyItemInserted(0);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public News getItem(int pos) {
        return list.get(pos);
    }

    public void removeItem(int pos) {
        list.remove(list.get(pos));
        notifyItemRemoved(pos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull RecyclerItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(News news) {
            binding.textView.setText(news.getTitle());
        }
    }
}
