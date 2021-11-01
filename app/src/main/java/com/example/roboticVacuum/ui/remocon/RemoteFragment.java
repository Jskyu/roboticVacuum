package com.example.roboticVacuum.ui.remocon;

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
import com.example.roboticVacuum.dto.HttpUrl;
import com.example.roboticVacuum.service.HttpService;

public class RemoteFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.remocon_ui, container, false);

        rootView.findViewById(R.id.btnRemoteUp).setOnClickListener(this);
        rootView.findViewById(R.id.btnRemoteDown).setOnClickListener(this);
        rootView.findViewById(R.id.btnRemoteRight).setOnClickListener(this);
        rootView.findViewById(R.id.btnRemoteLeft).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        final HttpService httpService = new HttpService();      // AsyncTask 생성
        switch (id) {
            case R.id.btnRemoteUp:
//                if(HttpService.IS_RECORDING) {
                Log.i("BUTTON CLICK", HttpUrl.POST_URL + " : UP");
                httpService.execute(HttpUrl.POST_URL.getValue(), HttpService.RECORD_NAME, Code.UP.getNumber());
//                }
                break;
            case R.id.btnRemoteDown:
                Log.i("BUTTON CLICK", HttpUrl.POST_URL + " : DOWN");
//                if(HttpService.IS_RECORDING) {
                httpService.execute(HttpUrl.POST_URL.getValue(), HttpService.RECORD_NAME, Code.DOWN.getNumber());
//                }
                break;
            case R.id.btnRemoteRight:
                Log.i("BUTTON CLICK", HttpUrl.POST_URL + " : RIGHT");
//                if(HttpService.IS_RECORDING) {
                httpService.execute(HttpUrl.POST_URL.getValue(), HttpService.RECORD_NAME, Code.RIGHT.getNumber());
//                }
                break;
            case R.id.btnRemoteLeft:
                Log.i("BUTTON CLICK", HttpUrl.POST_URL + " : LEFT");
//                if (HttpService.IS_RECORDING) {
                httpService.execute(HttpUrl.POST_URL.getValue(), HttpService.RECORD_NAME, Code.LEFT.getNumber());
//                }
                break;
        }
    }
}