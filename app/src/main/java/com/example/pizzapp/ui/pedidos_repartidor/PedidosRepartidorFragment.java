package com.example.pizzapp.ui.pedidos_repartidor;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzapp.EntregasPendientes;
import com.example.pizzapp.PedidoPendienteFragment;
import com.example.pizzapp.PizzAppDB;
import com.example.pizzapp.R;

public class PedidosRepartidorFragment extends Fragment {

    private PedidosRepartidorViewModel pedidosRepartidorViewModel;
    PizzAppDB db;
    TextView textViewVacio;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        pedidosRepartidorViewModel =
                new ViewModelProvider(this).get(PedidosRepartidorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pedidos_repartidor, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        textViewVacio = root.findViewById(R.id.textViewVacio);
        pedidosRepartidorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Verificación de cantidad de elementos, si no hay elementos muestra textView de Vacío
        int contador = 0;
        Cursor c = getPedidosAsignados();
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
        EntregasPendientes ArrayFragments [] = new EntregasPendientes [1000];
        Cursor c = getPedidosAsignados();
        EntregasPendientes entregasPendientes;

        int contador = 0;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido = c.getInt(0);
                entregasPendientes = new EntregasPendientes();
                String id = String.valueOf(id_pedido);
                transaction.add(R.id.pedidosRepartidorLayout, entregasPendientes, id);
                ArrayFragments[contador] = entregasPendientes;
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

    public Cursor getPedidosAsignados() {
        db = new PizzAppDB(getContext());
        Cursor c = db.getPedidosAsignados();
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