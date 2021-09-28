package com.example.roboticVacuum.service;

import android.widget.Button;

public class BtnEventService {

    private int clickCount = 0;
    private final BluetoothService bluetoothService = new BluetoothService();

    public void pressedWallFollowingButton(){
        //WALL FOLLOWING Button 클릭 이벤트 처리
    }
    public void pressedRandomButton(Button randomBtn){
        randomBtn.setText("click : " + ++clickCount);
        bluetoothService.sendCode("code001");
    }
    public void pressedCircleButton(){
        //CIRCLE Button 이벤트 처리
    }
    public void pressedWaveButton(){
        //WAVE Button 이벤트 처리
    }

    public void pressedUpButton(){
        //UP Button 이벤트 처리
    }
    public void pressedDownButton(){
        //DOWN Button 이벤트 처리
    }
    public void pressedRightButton(){
        //RIGHT Button 이벤트 처리
    }
    public void pressedLeftButton(){
        //LEFT Button 이벤트 처리
    }
}
