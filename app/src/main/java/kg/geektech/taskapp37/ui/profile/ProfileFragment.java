package kg.geektech.taskapp37.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import kg.geektech.taskapp37.Prefs;
import kg.geektech.taskapp37.R;
import kg.geektech.taskapp37.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ActivityResultLauncher<Intent> activityResultLauncher;
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
        saveUserName(prefs);

        if (!prefs.isImageSave().equals("")) {
            Glide.with(binding.imageView).load(prefs.isImageSave()).into(binding.imageView);
            change = true;

        }

    }


    private void saveUserName(Prefs prefs) {
        binding.editText.addTextChangedListener(new TextWatcher() {
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

        binding.editText.setText(prefs.isTextSave());
    }

    private void saveImage(Prefs prefs) {
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    Glide.with(binding.imageView).load(uri).into(binding.imageView);
                    prefs.saveImage(uri);
                    binding.imageView.setImageURI(uri);
                    change = true;

                });

        binding.imageView.setOnClickListener(v -> {
            if (change) {
                AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
                builder.setNeutralButton("Выбрать", (dialog, which) -> mGetContent.launch("image/*"));
                builder.setPositiveButton("Удалить", (dialog, which) -> binding.imageView.setImageResource(R.drawable.ic_baseline_supervisor_account_24));
                AlertDialog dialog = builder.create();
                dialog.show();
                change = false;
            }else{
                        mGetContent.launch("image/*"); }
                });
            }
        }
