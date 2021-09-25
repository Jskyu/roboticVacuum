package com.example.roboticVacuum.ui.mode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roboticVacuum.databinding.ModeUiBinding;

public class ModeFragment extends Fragment {

    private ModeViewModel modeViewModel;
    private ModeUiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        modeViewModel = new ViewModelProvider(this).get(ModeViewModel.class);

        binding = ModeUiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMode;
        modeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}