package com.example.roboticVacuum.ui.remocon;

import static com.example.roboticVacuum.service.HttpService.RECORD_NAME;

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
                Log.i("BUTTON CLICK ", "UP");
//                httpService.execute(HttpUrl.POST_URL.getValue(), HttpService.RECORD_NAME, Code.UP.getNumber());
                //저장 이름에 따라서 저장된 움직임(MOVE) 불러오는 테스트용 코드
                httpService.execute(HttpUrl.SELECT_URL.getValue(), RECORD_NAME);
//                }
                break;
            case R.id.btnRemoteDown:
                Log.i("BUTTON CLICK ", "DOWN");
//                if(HttpService.IS_RECORDING) {
                httpService.execute(HttpUrl.INSERT_URL.getValue(), RECORD_NAME, Code.DOWN.getNumber());
//                }
                break;
            case R.id.btnRemoteRight:
                Log.i("BUTTON CLICK ", "RIGHT");
//                if(HttpService.IS_RECORDING) {
                httpService.execute(HttpUrl.INSERT_URL.getValue(), RECORD_NAME, Code.RIGHT.getNumber());
//                }
                break;
            case R.id.btnRemoteLeft:
                Log.i("BUTTON CLICK ", "LEFT");
//                if (HttpService.IS_RECORDING) {
                httpService.execute(HttpUrl.INSERT_URL.getValue(), RECORD_NAME, Code.LEFT.getNumber());
//                }
                break;
        }
    }
}