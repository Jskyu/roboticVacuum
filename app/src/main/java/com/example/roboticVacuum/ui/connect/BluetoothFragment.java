package com.example.roboticVacuum.ui.connect;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roboticVacuum.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

//참고 : https://yeolco.tistory.com/80
public class BluetoothFragment extends Fragment {

    private static final UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //HC-06 UUID
    public BluetoothAdapter mBluetoothAdapter;
    public Set<BluetoothDevice> mDevices;
    private BluetoothSocket bSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private BluetoothDevice mRemoteDevice;
    public boolean onBT = false;
    public byte[] sendByte = new byte[4];
    private static final int REQUEST_ENABLE_BT = 1;
    private String nowBtName = "연결되어 있지 않습니다.";

    Thread BTSend = new Thread(new Runnable() {
        public void run() {
            try {
                mOutputStream.write(sendByte);    // 프로토콜 전송
            } catch (Exception e) {
                // 문자열 전송 도중 오류가 발생한 경우.
            }
        }
    });

    private TextView btStateTextView; // 블루투스 현재 상태 텍스트 뷰
    private Button btConnect; // 블루투스 연결 버튼

    private ViewGroup rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.bluetooth_ui, container, false);

        btStateTextView = rootView.findViewById(R.id.bluetoothStateTextView);
        btStateTextView.setText(nowBtName);
        
        btConnect = rootView.findViewById(R.id.btnSend);

        btConnect.setOnClickListener(v -> {
            connectBT();
        });

        return rootView;
    }

    private void connectBT() {
        if (!onBT) { //Connect
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) { //장치가 블루투스를 지원하지 않는 경우.
                Toast.makeText(rootView.getContext(), "Bluetooth 지원을 하지 않는 기기입니다.", Toast.LENGTH_SHORT)
                        .show();
            } else { // 장치가 블루투스를 지원하는 경우.
                if (!mBluetoothAdapter.isEnabled()) {
                    // 블루투스를 지원하지만 비활성 상태인 경우
                    // 블루투스를 활성 상태로 바꾸기 위해 사용자 동의 요청
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    // 블루투스를 지원하며 활성 상태인 경우
                    // 페어링된 기기 목록을 보여주고 연결할 장치를 선택.
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                    if (pairedDevices.size() > 0) {
                        // 페어링 된 장치가 있는 경우.
                        selectDevice();
                    } else {
                        // 페어링 된 장치가 없는 경우.
                        Toast.makeText(rootView.getContext(), "먼저 Bluetooth 설정에 들어가 페어링을 진행해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else { //DisConnect
            try {
                BTSend.interrupt();   // 데이터 송신 쓰레드 종료
                mInputStream.close();
                mOutputStream.close();
                bSocket.close();
                onBT = false;
                nowBtName = "BT OFF";
                btStateTextView.setText(nowBtName);
            } catch (Exception ignored) {
            }
        }
    }

    public void selectDevice() {
        mDevices = mBluetoothAdapter.getBondedDevices();
        final int mPairedDeviceCount = mDevices.size();

        if (mPairedDeviceCount == 0) {
            //  페어링 된 장치가 없는 경우
            Toast.makeText(rootView.getContext(), "장치를 페어링 해주세요!", Toast.LENGTH_SHORT).show();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());
        builder.setTitle("블루투스 장치 선택");

        // 페어링 된 블루투스 장치의 이름 목록 작성
        List<String> listItems = mDevices.stream().map(BluetoothDevice::getName).collect(Collectors.toList());
        listItems.add("취소");    // 취소 항목 추가

        final CharSequence[] items = listItems.toArray(new CharSequence[0]);

        builder.setItems(items, (dialog, item) -> {
            if (item != mPairedDeviceCount) {
                connectToSelectedDevice(items[item].toString());
            }
        });

        builder.setCancelable(false);    // 뒤로 가기 버튼 사용 금지
        builder.create().show();
    }

    public void connectToSelectedDevice(final String selectedDeviceName) {
        mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);

        Thread BTConnect = new Thread(() -> {
            try {
                // 소켓 생성
                bSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
                // RFCOMM 채널을 통한 연결
                bSocket.connect();

                // 데이터 송수신을 위한 스트림 열기
                mOutputStream = bSocket.getOutputStream();
                mInputStream = bSocket.getInputStream();

                nowBtName = selectedDeviceName;
                btStateTextView.setText(nowBtName);
                onBT = true;
            } catch (Exception e) {
                // 블루투스 연결 중 오류 발생
            }
        });
        BTConnect.start();
    }

    public BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;
        for (BluetoothDevice device : mDevices) {
            if (name.equals(device.getName())) {
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }

    public void sendData(String text) {
        // 문자열에 개행문자("\n")를 추가해줍니다.
        text += "\n";
        Log.d("BT", "OUTPUT DATA : " + text);
        try {
            sendByte = text.getBytes();
            BTSend.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public void sendBtData(int btLightPercent) throws IOException {
        //sendBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byte[] bytes = new byte[4];
        bytes[0] = (byte) 0xa5;
        bytes[1] = (byte) 0x5a;
        bytes[2] = 1; //command
        bytes[3] = (byte) btLightPercent;
        sendByte = bytes;
        BTSend.run();
    }*/
}
