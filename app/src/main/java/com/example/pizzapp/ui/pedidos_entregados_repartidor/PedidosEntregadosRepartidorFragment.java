package com.example.pizzapp.ui.pedidos_entregados_repartidor;

import android.database.Cursor;
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
import com.example.pizzapp.PedidosEntregadosFragment;
import com.example.pizzapp.PizzAppDB;
import com.example.pizzapp.R;

public class PedidosEntregadosRepartidorFragment extends Fragment {

    private PedidosEntregadosRepartidorViewModel pedidosEntregadosRepartidorViewModel;
    PizzAppDB db;
    TextView textViewVacio;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        pedidosEntregadosRepartidorViewModel =
                new ViewModelProvider(this).get(PedidosEntregadosRepartidorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pedidos_entregados_repartidor, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        textViewVacio = root.findViewById(R.id.textViewVacio);
        pedidosEntregadosRepartidorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Verificación de cantidad de elementos, si no hay elementos muestra textView de Vacío
        int contador = 0;
        Cursor c = getPedidosEntregados();
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
        PedidosEntregadosFragment ArrayFragments [] = new PedidosEntregadosFragment [1000];
        Cursor c = getPedidosEntregados();
        PedidosEntregadosFragment peidosEntregados;

        int contador = 0;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido = c.getInt(0);
                peidosEntregados = new PedidosEntregadosFragment();
                String id = String.valueOf(id_pedido);
                transaction.add(R.id.pedidosEntregadosRepartidorLayout, peidosEntregados, id);
                ArrayFragments[contador] = peidosEntregados;
                contador++;
            } while (c.moveToNext());

            for (int i = 0; i < ArrayFragments.length; i++) {
                if (ArrayFragments[i] != null) {
                    //System.out.println("Array fragments: " + ArrayFragments[i]);
                }
            }
        }
        transaction.commit();
    }

    public Cursor getPedidosEntregados() {
        db = new PizzAppDB(getContext());
        Cursor c = db.getPedidosEntregados();
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