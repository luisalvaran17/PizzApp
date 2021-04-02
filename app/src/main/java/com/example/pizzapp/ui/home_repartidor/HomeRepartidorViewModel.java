package com.example.pizzapp.ui.home_repartidor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeRepartidorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeRepartidorViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}