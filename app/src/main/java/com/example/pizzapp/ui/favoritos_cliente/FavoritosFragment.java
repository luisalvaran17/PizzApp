package com.example.pizzapp.ui.favoritos_cliente;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzapp.HomeMenuFragment;
import com.example.pizzapp.PizzAppDB;
import com.example.pizzapp.ProductoFavoritosFragment;
import com.example.pizzapp.ProductoPedidoFragment;
import com.example.pizzapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;

public class FavoritosFragment extends Fragment {

    private AdView mAdView;
    PizzAppDB db;
    TextView textViewVacio;

    private FavoritosViewModel favoritosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritosViewModel =
                new ViewModelProvider(this).get(FavoritosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favoritos, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        favoritosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        super.onCreate(savedInstanceState);

        //Publicidad con AdMod
        MobileAds.initialize(this.getContext(), "ca-app-pub-2910015119031549/4111267061");
        mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return root;
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        ProductoFavoritosFragment ArrayFragments[] = new ProductoFavoritosFragment[1000];
        Cursor c = getProductos();
        ProductoFavoritosFragment productoFavoritosFragment;

        int contador = 0;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_producto = c.getInt(0);
                productoFavoritosFragment = new ProductoFavoritosFragment();
                String id = String.valueOf(id_producto);
                transaction.add(R.id.favoritosClienteLayout, productoFavoritosFragment, id);
                ArrayFragments[contador] = productoFavoritosFragment;
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
    }*/
}