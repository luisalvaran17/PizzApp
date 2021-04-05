package com.example.pizzapp.ui.home_repartidor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzapp.PedidoPendienteFragment;
import com.example.pizzapp.R;
import com.example.pizzapp.ui.pedidos_repartidor.PedidosRepartidorFragment;

public class HomeRepartidorFragment extends Fragment {

    private HomeRepartidorViewModel homeRepartidorViewModel;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeRepartidorViewModel =
                new ViewModelProvider(this).get(HomeRepartidorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_repartidor, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        homeRepartidorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        PedidoPendienteFragment pedidoPendienteFragment1 = new PedidoPendienteFragment();
        transaction.add(R.id.homeRepartidorLayout, pedidoPendienteFragment1);

        PedidoPendienteFragment pedidoPendienteFragment2 = new PedidoPendienteFragment();
        transaction.add(R.id.homeRepartidorLayout, pedidoPendienteFragment2);

        PedidoPendienteFragment pedidoPendienteFragment3 = new PedidoPendienteFragment();
        transaction.add(R.id.homeRepartidorLayout, pedidoPendienteFragment3);

        transaction.commit();
    }
}