package com.example.pizzapp.ui.localizacion_repartidor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocalizacionRepartidorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LocalizacionRepartidorViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}