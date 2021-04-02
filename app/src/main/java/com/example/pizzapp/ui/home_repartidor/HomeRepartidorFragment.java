package com.example.pizzapp.ui.home_repartidor;

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

public class HomeRepartidorFragment extends Fragment {

    private HomeRepartidorViewModel homeRepartidorViewModel;

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
}