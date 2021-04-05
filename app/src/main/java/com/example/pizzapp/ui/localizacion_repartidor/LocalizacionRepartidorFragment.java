package com.example.pizzapp.ui.localizacion_repartidor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzapp.MapsFragment;
import com.example.pizzapp.R;

public class LocalizacionRepartidorFragment extends Fragment {

    private LocalizacionRepartidorViewModel localizacionRepartidorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        localizacionRepartidorViewModel =
                new ViewModelProvider(this).get(LocalizacionRepartidorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_localizacion_repartidor, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        // final Spinner spinner = root.findViewById(R.id.spinner);
        localizacionRepartidorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });

        // array direcciones

        // String direcciones_array [] = {"Carrera 8ª Oeste #26, Tuluá, Valle del Cauca", "Carrera 8a #26, Tuluá, Valle del Cauca"};
        // Create an ArrayAdapter using the string array and a default spinner layout
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, direcciones_array);
        // Specify the layout to use when the list of choices appears
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        // spinner.setAdapter(adapter);

        // spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        /**
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                selected_address = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
            **/
        return root;
    }


}