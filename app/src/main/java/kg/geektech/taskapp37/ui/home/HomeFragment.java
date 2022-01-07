package kg.geektech.taskapp37.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import kg.geektech.taskapp37.App;
import kg.geektech.taskapp37.R;
import kg.geektech.taskapp37.adapters.NewsAdapter;
import kg.geektech.taskapp37.databinding.FragmentHomeBinding;
import kg.geektech.taskapp37.interfaces.OnItemClickListener;
import kg.geektech.taskapp37.models.News;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NewsAdapter adapter;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
        List<News> list = App.getInstance().getDatabase().newsDao().getAll();
        adapter.addAll(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(v -> {
            openFragment(null);
        });
        setHasOptionsMenu(true);
        onItemClick();

        getParentFragmentManager().setFragmentResultListener("rk_news_add", getViewLifecycleOwner(), (requestKey, result) -> {
            News news = (News) result.getSerializable("add");
            Log.e("home", "text: " + news.getTitle());
            adapter.addItem(news);

        });

        getParentFragmentManager().setFragmentResultListener("rk_news_update", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("update");
                adapter.updateItem(news);
            }
        });

        initList();
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                News news = adapter.getItem(pos);
                Toast.makeText(requireContext(), news.getTitle(), Toast.LENGTH_SHORT).show();
                openFragment(news);
            }

            @Override
            public void onLongClick(int pos) {
/*
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Вы хотите удалить элемент?");
                builder.setPositiveButton("Удалить", (dialog, which) -> adapter.removeItem(pos));
                builder.setNegativeButton("Отмена", null);
                builder.show();
*/
                new AlertDialog.Builder(requireContext())
                        .setMessage("Вы хотите удалить элемент?")
                        .setPositiveButton("Удалить", (dialog, which) -> adapter.removeItem(pos))
                        .setNegativeButton("Отмена", null)
                        .show();
            }
        });
    }

    private void initList() {
        binding.recyclerView.setAdapter(adapter);
    }

    private void openFragment(News news) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        navController.navigate(R.id.newsFragment, bundle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_acb_sort, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sort) {
            if (item.getTitle() == requireContext().getString(R.string.SORT)) {
                adapter.setList(App.getInstance().getDatabase().newsDao().getAllByTitle());
                item.setTitle(R.string.SORTED);
                binding.recyclerView.setAdapter(adapter);

            } else {
                adapter.setList(App.getInstance().getDatabase().newsDao().getAll());
                item.setTitle(R.string.SORT);
                binding.recyclerView.setAdapter(adapter);

            }
            binding.recyclerView.setAdapter(adapter);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}