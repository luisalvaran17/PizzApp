package com.example.pizzapp.ui.pedidos_repartidor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PedidosRepartidorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PedidosRepartidorViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}