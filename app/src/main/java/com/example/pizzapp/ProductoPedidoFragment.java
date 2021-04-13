
package com.example.pizzapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductoPedidoFragment#} factory method to
 * create an instance of this fragment.
 */
public class ProductoPedidoFragment extends Fragment {

    public static String view;
    public static int botones [] = new int[100];
    public static Button buttons_array [] = new Button[100];
    public static int contador_button = 0;
    public static int contador2_button = 0;

    public static int text_view [] = new int[100];
    public static TextView texts_view_array [] = new TextView[100];
    public static int contador_text_view = 0;
    public static int contador2_text_view = 0;

    public static int text_view_prods [] = new int[100];
    public static TextView texts_view_array_prods [] = new TextView[100];
    public static int contador_text_view_prods = 0;
    public static int contador2_text_view_prods = 0;
    public PizzAppDB db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductoPedidoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EntregasPendientes.
     */
    // TODO: Rename and change types and number of parameters
    public void newInstanceButton(Button btn) {
        Cursor c = getPedidosAsignados();
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

    public void newInstanceText(TextView textViewPorciones, TextView textViewTel,
                                TextView textViewDireccion, TextView textViewIdPedido, TextView textViewTotalPrecio) {
        Cursor c = getPedidosAsignados();
        text_view[contador_text_view] = contador_text_view;
        texts_view_array[contador_text_view] = textViewPorciones;
        contador2_text_view = 0;

        int ids_pedidos[] = new int[10000];
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido = c.getInt(0);
                ids_pedidos[contador2_text_view] = id_pedido;
                contador2_text_view++;
            } while (c.moveToNext());
        }

        if (text_view[contador_text_view] == contador_text_view) {
            textViewPorciones.setTag(ids_pedidos[contador_text_view]);
            textViewTel.setTag(ids_pedidos[contador_text_view]);
            textViewDireccion.setTag(ids_pedidos[contador_text_view]);
            textViewIdPedido.setTag(ids_pedidos[contador_text_view]);
            textViewTotalPrecio.setTag(ids_pedidos[contador_text_view]);
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
        contador_text_view_prods = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_producto_pedidos, container, false);

        TextView textViewPorciones = v.findViewById(R.id.textViewPorciones);
        TextView textViewTel = v.findViewById(R.id.textViewTelf);
        TextView textViewDireccion = v.findViewById(R.id.textViewDireccion);
        TextView textViewIdPedido = v.findViewById(R.id.textViewIdPedido);
        TextView textViewTotalPrecio = v.findViewById(R.id.textViewTotalPrecio);
        TextView textViewProducto = v.findViewById(R.id.textViewIdsProductos);
        ImageView imageViewPizza = v.findViewById(R.id.imageView1);
        int[] galeria = {R.drawable.vegetariana, R.drawable.ranchera,
                R.drawable.hawaii, R.drawable.estofado, R.drawable.italiana, R.drawable.familia};
        newInstanceText(textViewPorciones, textViewTel, textViewDireccion, textViewIdPedido, textViewTotalPrecio);
        //newInstanceTextIdsProds(textViewProducto);
        db = new PizzAppDB(getContext());

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Cursor p = db.getIdPedidosProducto(id);
        Cursor c = db.getPedidos(id);
        int id_pedido = 0;
        int id_producto = 0;
        try {
            if (c.moveToFirst()) {
                do {
                    id_pedido = c.getInt(0);
                    String cantidad_porciones = c.getString(2);
                    String total_pago = c.getString(3);
                    String tel = c.getString(5);
                    String direccion = c.getString(4);
                    id_producto = c.getInt(9);


                    if (id_pedido == Integer.parseInt(textViewPorciones.getTag().toString())) {
                        textViewPorciones.setText("Cantidad: " + cantidad_porciones);
                        textViewTel.setText("Teléfono: " + tel);
                        textViewTotalPrecio.setText("$ " + total_pago);
                        String[] parts = direccion.split(",");
                        String direccion_simplificada = parts[0]; //
                        textViewDireccion.setText("Dirección: " + direccion_simplificada);
                        imageViewPizza.setImageResource(galeria[id_producto-1]);
                        textViewIdPedido.setText("# " + id_pedido);
                    }

                } while (c.moveToNext());
            }

        }catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        return v;
    }

    public Cursor getPedidosAsignados() {
        db = new PizzAppDB(getContext());
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Cursor c = db.getPedidos(id);
        return c;
    }


}