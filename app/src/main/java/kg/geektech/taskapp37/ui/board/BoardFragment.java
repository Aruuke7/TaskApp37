package kg.geektech.taskapp37.ui.board;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geektech.taskapp37.R;
import kg.geektech.taskapp37.databinding.FragmentBoardBinding;
import kg.geektech.taskapp37.interfaces.OnBoardStartClickListener;
import kg.geektech.taskapp37.adapters.BoardAdapter;

public class BoardFragment extends Fragment {

    private FragmentBoardBinding binding;
    BoardAdapter adapter = new BoardAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
        initDots();
        initSkip();
        clickStart();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    private void clickStart() {
        adapter.setOnBoardStartClickListener(new OnBoardStartClickListener() {
            @Override
            public void OnStartClick() {
                close();
            }
        });
    }


    private void initSkip() {
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == adapter.getItemCount() -1){
                    binding.btnSkip.setVisibility(View.INVISIBLE);
                }else {
                    binding.btnSkip.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    private void initViewPager() {
        binding.viewPager.setAdapter(adapter);
    }

    private void initDots() {
        binding.dotsIndicator.setViewPager2(binding.viewPager);
        binding.dotsIndicator.setDotsColor(Color.GRAY);
    }

    private void close(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}