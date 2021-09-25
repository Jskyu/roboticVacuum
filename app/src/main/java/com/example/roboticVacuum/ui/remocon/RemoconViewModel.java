package com.example.roboticVacuum.ui.remocon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RemoconViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RemoconViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Remocon UI");
    }

    public LiveData<String> getText() {
        return mText;
    }
}