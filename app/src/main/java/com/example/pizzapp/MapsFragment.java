package com.example.pizzapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pizzapp.ui.localizacion_repartidor.LocalizacionRepartidorFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

public class MapsFragment extends Fragment {
    PizzAppDB db;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        LocalizacionRepartidorFragment localizacionRepartidorFragment = new LocalizacionRepartidorFragment();

        @Override
        public void onMapReady(GoogleMap googleMap) {

            int isNightTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (isNightTheme) {
                case Configuration.UI_MODE_NIGHT_YES:
                    googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style_dark));
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    break;
            }

            // Dirección cliente
            LatLng tulua = new LatLng (4.083, -76.2);
            googleMap.addMarker(new MarkerOptions().position(tulua).title("Mi ubicación"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(tulua));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tulua, 14f));

            String direcciones_array [] = getMarkers();
            String title_marker = "";
            LatLng location_pedido;
            for (int i=0; i<direcciones_array.length; i++){
                if(direcciones_array[i] != null) {
                    location_pedido = getLocationFromAddress(getContext(), direcciones_array[i]);
                    String string = direcciones_array[i];
                    String[] parts = string.split(",");
                    String part1 = parts[0];
                    title_marker = part1;
                    googleMap.addMarker(new MarkerOptions()
                            .position(location_pedido)
                            .title(title_marker)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            .alpha(0.4f));
                }
                else{
                    break;
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public String[] getMarkers() {
        System.out.println("entra");
        db = new PizzAppDB(getContext());
        String direcciones_array [] = new String[100];
        int iterador = 0;
        try {
            Cursor c = db.getPedidos();
            // Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                // Recorremos el cursor hasta que no haya más registros
                do {
                    String direccion_pedido = c.getString(4);
                    direcciones_array[iterador] = direccion_pedido;
                    iterador++;
                } while (c.moveToNext());
            }
        }catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return direcciones_array;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}