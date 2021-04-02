package com.example.pizzapp.ui.perfil_repartidor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzapp.R;

public class PerfilRepartidorFragment extends Fragment {

    private PerfilRepartidorViewModel perfilRepartidorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilRepartidorViewModel =
                new ViewModelProvider(this).get(PerfilRepartidorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil_repartidor, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        perfilRepartidorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}