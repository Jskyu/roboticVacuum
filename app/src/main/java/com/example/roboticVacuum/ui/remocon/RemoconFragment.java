package com.example.roboticVacuum.ui.remocon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roboticVacuum.databinding.RemoconUiBinding;

public class RemoconFragment extends Fragment {

    private RemoconViewModel remoconViewModel;
    private RemoconUiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        remoconViewModel =
                new ViewModelProvider(this).get(RemoconViewModel.class);

        binding = RemoconUiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textRemocon;
        remoconViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}