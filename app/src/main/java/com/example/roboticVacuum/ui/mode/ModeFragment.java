package com.example.roboticVacuum.ui.mode;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roboticVacuum.R;
import com.example.roboticVacuum.dto.Code;
import com.example.roboticVacuum.service.BluetoothService;
import com.example.roboticVacuum.ui.connect.BluetoothFragment;

public class ModeFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.mode_ui, container, false);

        //버튼 추가 START
        rootView.findViewById(R.id.btnRandom).setOnClickListener(this);
        rootView.findViewById(R.id.btnCircle).setOnClickListener(this);
        rootView.findViewById(R.id.btnWallFollowing).setOnClickListener(this);
        rootView.findViewById(R.id.btnWave).setOnClickListener(this);
        //버튼 추가 END

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Code cd = Code.FAILED;
        int id = v.getId();
        switch (id) {
            case R.id.btnRandom:
                //btnRandom Click Event
                Log.i("MODE ", "RANDOM");
                cd = Code.RANDOM;
//                btnEventService.pressedRandomButton(findViewById(id));
                break;
            case R.id.btnCircle:
                Log.i("MODE ", "CIRCLE");
                cd = Code.CIRCLE;
                break;
            case R.id.btnWallFollowing:
                Log.i("MODE ", "WALL_FOLLOWING");
                cd = Code.WALL_FOLLOWING;
                break;
            case R.id.btnWave:
                Log.i("MODE ", "WAVE");
                cd = Code.WAVE;
                break;
        }

        BluetoothService.sendData(cd.getCode());
    }
}