package kg.geektech.taskapp37;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import kg.geektech.taskapp37.databinding.FragmentPhotoBinding;

public class PhotoFragment extends Fragment {
     private @NonNull FragmentPhotoBinding binding;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentPhotoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savePhoto();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @SuppressLint("ResourceType")
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_activity_main);
                navController.navigateUp();
            }
        });
    }

    private void savePhoto() {
        String photo = requireArguments().getString("photo");
        Log.d("ololo", "onViewCreated: " + photo);
        Glide.with(binding.photo).load(photo).into(binding.photo);
    }
}