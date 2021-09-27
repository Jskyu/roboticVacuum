package com.example.roboticVacuum.service;

import android.widget.Button;

public class ModeBtnEventService {

    private int clickCount = 0;

    public void pressedWallFollowingButton(){
        //WALL FOLLOWING Button 클릭 이벤트 처리
    }
    public void pressedRandomButton(Button randomBtn){
        randomBtn.setText("click : " + ++clickCount);
    }
    public void pressedCircleButton(){
        //CIRCLE Button 이벤트 처리
    }
    public void pressedWaveButton(){
        //WAVE Button 이벤트 처리
    }
}
