package kg.geektech.taskapp37.ui.profile;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import kg.geektech.taskapp37.Prefs;
import kg.geektech.taskapp37.R;
import kg.geektech.taskapp37.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ActivityResultLauncher<String> activityResultLauncher;
    private boolean change = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Prefs prefs = new Prefs(requireContext());
        saveImage(prefs);
        saveData(prefs);
        sharePhoto(prefs);
        initClick();


        if (!prefs.getSavedImage().equals("")) {
            Glide.with(binding.imageView).load(prefs.getSavedImage()).into(binding.imageView);
            change = true;
        }
    }

    private void sharePhoto(Prefs prefs) {
        binding.imageView.setOnClickListener(v -> {
            if (!prefs.getSavedImage().equals("")) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                Bundle bundle = new Bundle();
                bundle.putString("photo", prefs.getSavedImage());
                navController.navigate(R.id.photoFragment, bundle);

            } else
                Toast.makeText(requireContext(), "???????????????????? ??????????????????????", Toast.LENGTH_LONG).show();
        });
    }


    private void initClick() {
        binding.signOut.setOnClickListener(v -> {

            new AlertDialog.Builder(requireContext())
                    .setMessage("Do you want to log out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.loginFragment);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void saveData(Prefs prefs) {
        binding.username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.saveText(s.toString());
            }
        });
        binding.username.setText(prefs.isTextSave());

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            prefs.saveEmail(s.toString());
            }
        });
        binding.email.setText(prefs.getSavedEmail());

        binding.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            prefs.savePhone(s.toString());
            }
        });
        binding.phone.setText(prefs.getSavedPnone());

        binding.gender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            prefs.saveGender(s.toString());
            }
        });

        binding.gender.setText(prefs.getSavedGender());

        binding.date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            prefs.saveDate(s.toString());
            }
        });
        binding.date.setText(prefs.getSavedDate());
    }


    private void saveImage(Prefs prefs) {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
            if (prefs.getSavedImage() != null) {
                Glide.with(binding.imageView).load(uri).into(binding.imageView);
                prefs.saveImage(uri);
                binding.imageView.setImageURI(uri);
                change = true;
            }else {
                binding.imageView.setImageResource(R.drawable.ic_baseline_supervisor_account_24);
            }
                });



        binding.addPhoto.setOnClickListener(v -> {
            if (change) {
                final CharSequence[] options = {"Select photo", "Delete"};
                new AlertDialog.Builder(requireContext())
                        .setItems(options, (dialog, which) -> {
                            if (options[which].equals(options[0])){
                                activityResultLauncher.launch("image/*");
                            }else  if (options[which].equals(options[1])){
                                binding.imageView.setImageResource(R.drawable.ic_baseline_supervisor_account_24);
                                prefs.deleteUserImage(Uri.parse(prefs.getSavedImage()));
                            }
                        }).show();
            } else {
                activityResultLauncher.launch("image/*");
            }
            change = false;
        });
    }
}
