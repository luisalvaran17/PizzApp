
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductoFavoritosFragment#} factory method to
 * create an instance of this fragment.
 */
public class ProductoFavoritosFragment extends Fragment {

    PizzAppDB db;

    public static int icon_view [] = new int[100];
    public static ImageView icon_view_array [] = new ImageView[100];
    public static int contador_icon_view = 0;
    public static int contador2_icon_view = 0;

    public static int text_view [] = new int[100];
    public static TextView texts_view_array [] = new TextView[100];
    public static int contador_text_view = 0;
    public static int contador2_text_view = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductoFavoritosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EntregasPendientes.
     */
    // TODO: Rename and change types and number of parameters

    public void newInstanceImage(ImageView imageView) {
        Cursor c = getProductos();
        icon_view[contador_icon_view] = contador_icon_view;
        icon_view_array[contador_icon_view] = imageView;
        contador2_icon_view = 0;

        int ids_productos [] = new int[10000];
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m??s registros
            do {
                int id_producto = c.getInt(0);
                ids_productos[contador2_icon_view] = id_producto;
                contador2_icon_view++;
            } while (c.moveToNext());
        }

        if(icon_view[contador_icon_view] == contador_icon_view) {
            imageView.setTag(ids_productos[contador_icon_view]);
            contador_icon_view++;
        }
    }

    public void newInstanceText(TextView textViewPrecioMenu, TextView textViewNombreProducto) {
        Cursor c = getProductos();
        text_view[contador_text_view] = contador_text_view;
        texts_view_array[contador_text_view] = textViewPrecioMenu;
        contador2_text_view = 0;

        int ids_productos[] = new int[10000];
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m??s registros
            do {
                int id_producto = c.getInt(0);
                ids_productos[contador2_text_view] = id_producto;
                contador2_text_view++;
            } while (c.moveToNext());
        }

        if (text_view[contador_text_view] == contador_text_view) {
            textViewPrecioMenu.setTag(ids_productos[contador_text_view]);
            textViewNombreProducto.setTag(ids_productos[contador_text_view]);
            contador_text_view++;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contador_icon_view=0;
        contador_text_view=0;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorito_pedidos, container, false);
        TextView textViewPrecioMenu=v.findViewById(R.id.textViewTotalPrecio2);
        TextView textViewNombreProducto=v.findViewById(R.id.textViewPorciones2);
        ImageView imageViewPizza = v.findViewById(R.id.imageView12);
        int[] galeria = {R.drawable.vegetariana, R.drawable.ranchera,
                R.drawable.hawaii, R.drawable.estofado, R.drawable.italiana, R.drawable.familia};

        newInstanceText(textViewPrecioMenu,textViewNombreProducto);

        db = new PizzAppDB(getContext());

        //String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Cursor c = db.getProductos();
        int id_producto = 0;

        try {
            if (c.moveToFirst()) {
                do {
                    id_producto = c.getInt(0);
                    String precio = c.getString(3);
                    String nombre = c.getString(1);

                    if(id_producto == Integer.parseInt(textViewPrecioMenu.getTag().toString())){
                        textViewNombreProducto.setText(" " + nombre);
                        textViewPrecioMenu.setText("$ " + precio);
                        imageViewPizza.setImageResource(galeria[id_producto-1]);
                    }
                } while (c.moveToNext());
            }
        }catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        return v;

    }

    public Cursor getProductos() {
        db = new PizzAppDB(getContext());
        Cursor c = db.getProductos();
        try {
            int cantidad_pedidos_pendientes = 0;
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya m??s registros
                do {
                    int id_producto = c.getInt(0);
                    String nombre_producto = c.getString(1);
                    String precio_producto = c.getString(3);
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