package com.example.pizzapp.ui.home_cliente;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizzapp.Conexion;
import com.example.pizzapp.HomeMenuFragment;
import com.example.pizzapp.PedidoPendienteFragment;
import com.example.pizzapp.PizzAppDB;
import com.example.pizzapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    TextView tv_nombrePizza, tv_precioPizza;

    private ImageSwitcher imageSwitcher;
    private int[] galeria = {R.drawable.vegetariana, R.drawable.ranchera,
            R.drawable.hawaii, R.drawable.estofado, R.drawable.italiana, R.drawable.familia};
    private int posicion;
    private static final int DURACION = 9000;
    private Timer timer = null;

    PizzAppDB db;

    public ArrayList<String> arregloNombres = new ArrayList<String>();
    public ArrayList<String> arregloPrecios = new ArrayList<String>();

    //link mocky pizzas
    String url = "https://run.mocky.io/v3/8479739c-e5f6-4919-b7e9-655b7a668fae";

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //TextViews de promociones
        tv_nombrePizza = root.findViewById(R.id.textViewNombrePizza);
        tv_precioPizza = root.findViewById(R.id.textViewTotalPrecio);

        /*
        data from mocky.io de pizzas
         */
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        JsonObjectRequest StringRequest = new JsonObjectRequest(
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Pizzas");
                            db = new PizzAppDB(getContext());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String nombre_producto = jsonObject.getString("nombre_producto");
                                String precio_producto = jsonObject.getString("precio_producto");
                                int id_producto = jsonObject.getInt("id_producto");
                                String tipo_pizza = jsonObject.getString("tipo_pizza");
                                String cant_porciones = jsonObject.getString("cantidad_porciones");
                                String id_pizzeria = jsonObject.getString("id_pizzeria");

                               /* db.insertProducto(id_producto,nombre_producto,tipo_pizza,precio_producto
                                        ,cant_porciones,id_pizzeria);*/

                                arregloNombres.add("  " + nombre_producto);
                                arregloPrecios.add("$ " + precio_producto);

                            }
                            startSlider(arregloNombres, arregloPrecios, tv_nombrePizza, tv_precioPizza);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(StringRequest);

        //ImageSwitcher para el slide de promociones y sus respectivos precios
        imageSwitcher = (ImageSwitcher) root.findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                return imageView;
            }
        });

        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);
        return root;
    }

    public void startSlider(ArrayList<String> arregloNombres, ArrayList<String> arregloPrecios, TextView tv_nombrePizza, TextView tv_precioPizza) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if(getActivity() == null)
                    return;
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(galeria[posicion]);
                        tv_nombrePizza.setText(arregloNombres.get(posicion));
                        tv_precioPizza.setText(arregloPrecios.get(posicion));
                        posicion++;
                        if (posicion == galeria.length) {
                            posicion = 0;
                        }
                    }
                });
            }

        }, 0, DURACION);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        HomeMenuFragment ArrayFragments[] = new HomeMenuFragment[1000];
        Cursor c = getProductos();
        HomeMenuFragment homeMenuFragment;

        int contador = 0;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_producto = c.getInt(0);
                homeMenuFragment = new HomeMenuFragment();
                String id = String.valueOf(id_producto);
                transaction.add(R.id.linearMenu, homeMenuFragment, id);
                ArrayFragments[contador] = homeMenuFragment;
                contador++;
            } while (c.moveToNext());

            for (int i = 0; i < ArrayFragments.length; i++) {
                if (ArrayFragments[i] != null) {
                    //System.out.println("Array fragments: " + ArrayFragments[i]);
                }
            }
            System.out.println(contador);
            if (contador != 0) {
                //textViewVacio.setText(" ");
            }
        }
        transaction.commit();
    }

    public Cursor getProductos() {
        db = new PizzAppDB(getContext());
        Cursor c = db.getProductos();
        try {
            int cantidad_pedidos_pendientes = 0;
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    int id_producto = c.getInt(0);
                    String nombre_producto = c.getString(1);
                    String precio_producto = c.getString(3);
                    cantidad_pedidos_pendientes++;
                } while (c.moveToNext());
                return c;
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return c;
    }
}
