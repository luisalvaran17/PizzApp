package com.example.pizzapp.ui.home_repartidor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.example.pizzapp.EntregasPendientes;
import com.example.pizzapp.LoginActivity;
import com.example.pizzapp.PedidoPendienteFragment;
import com.example.pizzapp.PizzAppDB;
import com.example.pizzapp.R;
import com.example.pizzapp.RegistroActivity;
import com.example.pizzapp.ui.pedidos_repartidor.PedidosRepartidorFragment;

public class HomeRepartidorFragment extends Fragment {
    PizzAppDB db;
    private HomeRepartidorViewModel homeRepartidorViewModel;
    TextView textViewVacio;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeRepartidorViewModel =
                new ViewModelProvider(this).get(HomeRepartidorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_repartidor, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        textViewVacio = root.findViewById(R.id.textViewVacio);

        homeRepartidorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Verificación de cantidad de elementos, si no hay elementos muestra textView de Vacío
        int contador = 0;
        Cursor c = getPedidosNoAsignados();
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                contador++;
            } while (c.moveToNext());

            System.out.println(contador);
            if (contador != 0) {
                textViewVacio.setVisibility(View.GONE);
            }
        }

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        PedidoPendienteFragment ArrayFragments [] = new PedidoPendienteFragment [1000];
        Cursor c = getPedidosNoAsignados();
        PedidoPendienteFragment pedidoPendienteFragment;

        int contador = 0;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido = c.getInt(0);
                pedidoPendienteFragment = new PedidoPendienteFragment();
                String id = String.valueOf(id_pedido);
                transaction.add(R.id.homeRepartidorLayout, pedidoPendienteFragment, id);
                ArrayFragments[contador] = pedidoPendienteFragment;
                contador++;
            } while (c.moveToNext());

            for (int i=0; i<ArrayFragments.length; i++) {
                if(ArrayFragments[i] != null) {
                    //System.out.println("Array fragments: " + ArrayFragments[i]);
                }
            }
            System.out.println(contador);
            if (contador != 0){
                //textViewVacio.setText(" ");
            }
        }
        transaction.commit();
    }

    public Cursor getPedidosNoAsignados() {
        db = new PizzAppDB(getContext());
        Cursor c = db.getPedidosNoAsignados();
        try {
            int cantidad_pedidos_pendientes = 0;
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    int id_pedido = c.getInt(0);
                    String fecha_pedido = c.getString(1);
                    String cant_producto = c.getString(2);
                    String total_pago = c.getString(3);
                    String direccion_pedido = c.getString(4);
                    String entregado_check = c.getString(5);
                    int id_usuario = c.getInt(6);
                    int id_producto = c.getInt(7);
                    cantidad_pedidos_pendientes++;
                } while (c.moveToNext());
                return c;
            }
        }catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    return c;
    }

}