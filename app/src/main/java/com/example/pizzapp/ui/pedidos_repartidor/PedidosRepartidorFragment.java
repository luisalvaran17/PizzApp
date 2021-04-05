package com.example.pizzapp.ui.pedidos_repartidor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzapp.EntregasPendientes;
import com.example.pizzapp.PedidoPendienteFragment;
import com.example.pizzapp.R;

public class PedidosRepartidorFragment extends Fragment {

    private PedidosRepartidorViewModel pedidosRepartidorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        pedidosRepartidorViewModel =
                new ViewModelProvider(this).get(PedidosRepartidorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pedidos_repartidor, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        pedidosRepartidorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        for (int i=0; i<3; i++){
            EntregasPendientes pedidoRepartidor = new EntregasPendientes();
            transaction.add(R.id.pedidosRepartidorLayout, pedidoRepartidor);
        }

        transaction.commit();
    }
}