package com.example.roboticVacuum.ui.mode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roboticVacuum.R;

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRandom:
                //btnRandom Click Event
                Log.i("BUTTON CLICK", "RANDOM");
//                btnEventService.pressedRandomButton(findViewById(id));
                break;
            case R.id.btnCircle:
                Log.i("BUTTON CLICK", "CIRCLE");
                break;
            case R.id.btnWallFollowing:
                Log.i("BUTTON CLICK", "WALL_FOLLOWING");
                break;
            case R.id.btnWave:
                Log.i("BUTTON CLICK", "WAVE");
                break;
        }
    }
}