package com.example.roboticVacuum.ui.connect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.roboticVacuum.databinding.CameraUiBinding;

public class BluetoothFragment extends Fragment {

    private BluetoothViewModel bluetoothViewModel;
    private CameraUiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bluetoothViewModel = new ViewModelProvider(this).get(BluetoothViewModel.class);

        binding = CameraUiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBluetooth;
        bluetoothViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}