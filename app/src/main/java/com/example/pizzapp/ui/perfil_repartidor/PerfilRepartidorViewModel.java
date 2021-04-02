package com.example.pizzapp.ui.perfil_repartidor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerfilRepartidorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PerfilRepartidorViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}