package com.example.roboticVacuum.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothService {

    public static final UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //HC-06 UUID
    public static BluetoothAdapter mBluetoothAdapter;
    public static Set<BluetoothDevice> mDevices;
    public static BluetoothSocket bSocket;
    public static OutputStream mOutputStream;
    public static InputStream mInputStream;
    public static BluetoothDevice mRemoteDevice;
    public static boolean onBT = false;
    public static byte[] sendByte = new byte[4];
    public static final int REQUEST_ENABLE_BT = 1;
    //블루투스 연결 로직

    public static Thread BTSend = new Thread(new Runnable() {
        public void run() {
            try {
                mOutputStream.write(sendByte);    // 프로토콜 전송
            } catch (Exception e) {
                // 문자열 전송 도중 오류가 발생한 경우.
            }
        }
    });

    /**
     * method sendCode()
     * 어플리케이션 -> 블루투스 -> 아두이노 -> 동작
     * 앱에서 아두이노로 코드를 전송, 아두이노에서 로직 처리 하는게 맞는듯,,? 다른 아이디어 있으면 적어줘요
     */
    public static void sendData(String text) {
        // 문자열에 개행문자("\n")를 추가해줍니다.
        text += "\n";
        Log.d("BT", "OUTPUT DATA : " + text);
        try {
            sendByte = text.getBytes();
            BTSend.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}