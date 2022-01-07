package kg.geektech.taskapp37.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kg.geektech.taskapp37.databinding.RecyclerItemBinding;
import kg.geektech.taskapp37.interfaces.OnItemClickListener;
import kg.geektech.taskapp37.models.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private RecyclerItemBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));

        if (position % 2 == 0)
            binding.textView.setBackgroundColor(Color.GREEN);
        else
            binding.textView.setBackgroundColor(Color.YELLOW);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
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

    public void updateItem(News news) {
        int index = list.indexOf(news);
        list.set(index, news);
        notifyItemChanged(index);
    }

    public void addAll(List<News> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setList(List<News> allByTitle) {
        this.list = (ArrayList<News>) allByTitle;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull RecyclerItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            itemView.getRoot().setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            binding.getRoot().setOnLongClickListener(v -> {
                onItemClickListener.onLongClick(getAdapterPosition());
                return true;
            });
        }

        public void bind(News news) {
            binding.textView.setText(news.getTitle());
            binding.time.setText(new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss").format(news.getCreatedAt()));
        }
    }
}
