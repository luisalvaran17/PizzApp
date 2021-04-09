package com.example.pizzapp.ui.pedidos_entregados_repartidor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PedidosEntregadosRepartidorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PedidosEntregadosRepartidorViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}