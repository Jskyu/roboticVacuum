package com.example.roboticVacuum.ui.remocon;

import static com.example.roboticVacuum.service.HttpService.IS_RECORDING;
import static com.example.roboticVacuum.service.HttpService.RECORD_NAME;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roboticVacuum.R;
import com.example.roboticVacuum.dto.Code;
import com.example.roboticVacuum.dto.HttpUrl;
import com.example.roboticVacuum.service.BluetoothService;
import com.example.roboticVacuum.service.HttpService;

public class RemoteFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private TextView recordNameTextView;
    private Button recordBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.remocon_ui, container, false);

        rootView.findViewById(R.id.btnRemoteUp).setOnTouchListener(this);
        rootView.findViewById(R.id.btnRemoteDown).setOnTouchListener(this);
        rootView.findViewById(R.id.btnRemoteRight).setOnTouchListener(this);
        rootView.findViewById(R.id.btnRemoteLeft).setOnTouchListener(this);

        rootView.findViewById(R.id.btnRemoteCenter).setOnClickListener(this);

        recordNameTextView = rootView.findViewById(R.id.txtRecord);
        recordBtn = rootView.findViewById(R.id.btnRecord);
        recordBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Code cd = Code.FAILED;
        int id = v.getId();
        final HttpService httpService = new HttpService();      // AsyncTask 생성
        switch (id) {
            case R.id.btnRemoteCenter:
                cd = Code.CENTER;
                Log.i("REMOTE ", "CENTER");
                if (IS_RECORDING) {
                    httpService.execute(HttpUrl.INSERT_URL.getValue(), RECORD_NAME, Code.CENTER.getCode());
                }
                break;
            case R.id.btnRecord:
                if (recordBtn.getText().toString().equals("On")) {
                    IS_RECORDING = true;
                    RECORD_NAME = recordNameTextView.getText().toString();
                    recordNameTextView.setEnabled(false);
                    Log.i("RECORD ", RECORD_NAME);
                } else if (recordBtn.getText().toString().equals("Off")) {
                    IS_RECORDING = false;
                    recordNameTextView.setEnabled(true);
                }
                break;
        }
        if (id != R.id.btnRecord) BluetoothService.sendData(cd.getCode());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        Code cd = Code.FAILED;
        switch (action) {
            case MotionEvent.ACTION_DOWN: //Pressed
                int id = v.getId();
                final HttpService httpService = new HttpService();      // AsyncTask 생성
                switch (id) {
                    case R.id.btnRemoteUp:
                        Log.i("REMOTE ", "UP");
                        cd = Code.UP;
                        if (IS_RECORDING) {
                            httpService.execute(HttpUrl.INSERT_URL.getValue(), HttpService.RECORD_NAME, Code.UP.getCode());
//                    저장 이름에 따라서 저장된 움직임(MOVE) 불러오는 테스트용 코드
//                    httpService.execute(HttpUrl.SELECT_URL.getValue(), RECORD_NAME);
                        }
                        break;
                    case R.id.btnRemoteDown:
                        Log.i("REMOTE ", "DOWN");
                        cd = Code.DOWN;
                        if (IS_RECORDING) {
                            httpService.execute(HttpUrl.INSERT_URL.getValue(), RECORD_NAME, Code.DOWN.getCode());
                        }
                        break;
                    case R.id.btnRemoteRight:
                        Log.i("REMOTE ", "RIGHT");
                        cd = Code.RIGHT;
                        if (IS_RECORDING) {
                            httpService.execute(HttpUrl.INSERT_URL.getValue(), RECORD_NAME, Code.RIGHT.getCode());
                        }
                        break;
                    case R.id.btnRemoteLeft:
                        cd = Code.LEFT;
                        Log.i("REMOTE ", "LEFT");
                        if (IS_RECORDING) {
                            httpService.execute(HttpUrl.INSERT_URL.getValue(), RECORD_NAME, Code.LEFT.getCode());
                        }
                        break;
                    case R.id.btnRemoteCenter:
                        cd = Code.CENTER;
                        Log.i("REMOTE ", "CENTER");
                        if (IS_RECORDING) {
                            httpService.execute(HttpUrl.INSERT_URL.getValue(), RECORD_NAME, Code.CENTER.getCode());
                        }
                        break;
                    case R.id.btnRecord:
                        if (recordBtn.getText().toString().equals("On")) {
                            IS_RECORDING = true;
                            RECORD_NAME = recordNameTextView.getText().toString();
                            recordNameTextView.setEnabled(false);
                            Log.i("RECORD ", RECORD_NAME);
                        } else if (recordBtn.getText().toString().equals("Off")) {
                            IS_RECORDING = false;
                            recordNameTextView.setEnabled(true);
                            Log.i("RECORD ", "OFF");
                        }
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("REMOTE", "RELEASE");
                break;
        }
        BluetoothService.sendData(cd.getCode());
        return !cd.equals(Code.FAILED);
    }
}