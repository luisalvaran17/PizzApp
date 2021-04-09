package com.example.pizzapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PedidoPendienteFragment#} factory method to
 * create an instance of this fragment.
 */
public class PedidoPendienteFragment extends Fragment {
    public static String view;
    public static int botones [] = new int[100];
    public static Button buttons_array [] = new Button[100];
    public static int contador_button = 0;
    public static int contador2_button = 0;

    public static int text_view [] = new int[100];
    public static TextView texts_view_array [] = new TextView[100];
    public static int contador_text_view = 0;
    public static int contador2_text_view = 0;

    public static int icon_view [] = new int[100];
    public static ImageView icon_view_array [] = new ImageView[100];
    public static int contador_icon_view = 0;
    public static int contador2_icon_view = 0;

    DatabaseReference database;
    public PizzAppDB db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PedidoPendienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PedidoPendiente.
     */
    // TODO: Rename and change types and number of parameters
    public void newInstanceButton(Button btn) {
        Cursor c = getPedidosNoAsignados();
        botones[contador_button] = contador_button;
        buttons_array[contador_button] = btn;
        contador2_button = 0;

        int ids_pedidos [] = new int[10000];
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido = c.getInt(0);
                ids_pedidos[contador2_button] = id_pedido;
                contador2_button++;
            } while (c.moveToNext());
        }

        if(botones[contador_button] == contador_button) {
            btn.setTag(ids_pedidos[contador_button]);
            contador_button++;
        }
    }

    public void newInstanceImage(ImageView imageView) {
        Cursor c = getPedidosNoAsignados();
        icon_view[contador_icon_view] = contador_icon_view;
        icon_view_array[contador_icon_view] = imageView;
        contador2_icon_view = 0;

        int ids_pedidos [] = new int[10000];
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido = c.getInt(0);
                ids_pedidos[contador2_icon_view] = id_pedido;
                contador2_icon_view++;
            } while (c.moveToNext());
        }

        if(icon_view[contador_icon_view] == contador_icon_view) {
            imageView.setTag(ids_pedidos[contador_icon_view]);
            contador_icon_view++;
        }
    }

    public void newInstanceText(TextView textViewAsignar) {
        Cursor c = getPedidosNoAsignados();
        text_view[contador_text_view] = contador_text_view;
        texts_view_array[contador_text_view] = textViewAsignar;
        contador2_text_view = 0;

        int ids_pedidos [] = new int[10000];
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido = c.getInt(0);
                ids_pedidos[contador2_text_view] = id_pedido;
                contador2_text_view++;
            } while (c.moveToNext());
        }

        if(text_view[contador_text_view] == contador_text_view) {
            textViewAsignar.setTag(ids_pedidos[contador_text_view]);
            contador_text_view++;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        contador_button = 0;
        contador_text_view = 0;
        contador_icon_view = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pedido_pendiente, container, false);
        Button btnDetalle = v.findViewById(R.id.buttonDetallesPedido);
        TextView textAsignarentrega = v.findViewById(R.id.textViewAsignarPedido);
        ImageView imageViewPlus = v.findViewById(R.id.imageIconAsignarPedido);
        newInstanceText(textAsignarentrega);
        newInstanceButton(btnDetalle);
        newInstanceImage(imageViewPlus);

        // Button, asignación tag para diferenciar los botones
        btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println(btnDetalle.getTag());
                db = new PizzAppDB(getContext());
                Cursor c = db.getPedido(btnDetalle.getTag().toString());

                String fecha_pedido = "";
                String cant_producto = "";
                String total_pago = "";
                String direccion_pedido = "";
                int id_pedido = 0;
                try {
                    if (c.moveToFirst()) {
                        do {
                            id_pedido = c.getInt(0);
                            fecha_pedido = c.getString(1);
                            cant_producto = c.getString(2);
                            total_pago = c.getString(3);
                            direccion_pedido = c.getString(4);
                            String entregado_check = c.getString(5);
                            int id_usuario = c.getInt(6);
                            int id_producto = c.getInt(7);
                        } while (c.moveToNext());
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Detalles pedido");
                    builder.setMessage("ID pedido:" + id_pedido + "\nFecha pedido:  " + fecha_pedido +
                            "\nCantidad producto: " + cant_producto + "\nTotal: " + total_pago +
                            "\nDirección: " + direccion_pedido);
                    builder.setPositiveButton("Aceptar", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }catch (Exception e) {
                    e.getMessage();
                    e.printStackTrace();
                }
            }
        });

        // Text view, asignación tag para diferenciar los los textview
        textAsignarentrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println(textAsignarentrega.getTag());
                db = new PizzAppDB(getContext());
                Cursor c = db.getPedido(textAsignarentrega.getTag().toString());

                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String id_repartidor = id;  //  -> PENDIENTE: Traer id del login
                int id_pedido = 0;

                try {
                    if (c.moveToFirst()) {
                        do {
                            id_pedido = c.getInt(0);
                        } while (c.moveToNext());
                    }
                    Toast.makeText(getContext(), "Pedido asignado", Toast.LENGTH_LONG).show();
                    db.insertAsignacion(id_repartidor, id_pedido);
                    db.updatePedidoAsignado(id_pedido);
                    textAsignarentrega.setEnabled(false);
                }catch (Exception e) {
                    e.getMessage();
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Pedido ya asignado", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Image view Plus icon, asignación tag para diferenciar los los icons plus
        imageViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), imageViewPlus.getTag().toString(), Toast.LENGTH_SHORT).show();
                db = new PizzAppDB(getContext());
                Cursor c = db.getPedido(imageViewPlus.getTag().toString());


                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String id_repartidor = id;  //  -> PENDIENTE: Traer id del login
                int id_pedido = 0;

                try {
                    if (c.moveToFirst()) {
                        do {
                            id_pedido = c.getInt(0);
                        } while (c.moveToNext());
                    }
                    Toast.makeText(getContext(), "Pedido asignado", Toast.LENGTH_LONG).show();
                    db.insertAsignacion(id_repartidor, id_pedido);
                    db.updatePedidoAsignado(id_pedido);
                    imageViewPlus.setEnabled(false);
                }catch (Exception e) {
                    e.getMessage();
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se pudo asignar el pedido", Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
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