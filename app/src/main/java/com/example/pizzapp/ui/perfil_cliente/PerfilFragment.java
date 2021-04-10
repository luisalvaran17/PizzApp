package com.example.pizzapp.ui.perfil_cliente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pizzapp.ActivityBarCliente;
import com.example.pizzapp.LoginActivity;
import com.example.pizzapp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment implements View.OnClickListener {

    private PerfilViewModel perfilViewModel;

    Button btnCerrarSesion, btnConfirmarContrasena, btnCancelar;
    TextView tvName, tvEmail, tvTel, tvDireccion, tvCompras, tvCambiarContrasena;
    EditText etContrasenaActual, etNuevaContrasena;
    RatingBar calificacionEstrellas;
    DatabaseReference database;

    //Actualizar contraseña
    String contrasena_antigua = "";
    String contrasena_nueva = "";

    float cantStars;

    static final int SIGN_IN_CODE = 202;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        perfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        btnCerrarSesion = root.findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(this);

        btnConfirmarContrasena = root.findViewById(R.id.btnConfirmarContrasena);
        btnConfirmarContrasena.setOnClickListener(this);

        btnCancelar = root.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);

        tvName = root.findViewById(R.id.tvName);
        tvEmail = root.findViewById(R.id.tvEmail);
        tvTel = root.findViewById(R.id.tvTel);
        tvDireccion = root.findViewById(R.id.tvDireccion);
        tvCompras = root.findViewById(R.id.tvCompras);

        tvCambiarContrasena = root.findViewById(R.id.tvCambiarContrasena);

        etContrasenaActual = root.findViewById(R.id.etContrasenaActual);

        etNuevaContrasena = root.findViewById(R.id.etNuevaContrasena);

        calificacionEstrellas = root.findViewById(R.id.calificacionEstrellas);

        cantStars = calificacionEstrellas.getRating();

        cargarDatos();


        ///////////////


        return root;
    }

    public void cargarDatos(){

        //Mostrar los datos en el perfil

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference();

        database.child("Users").child("Clientes").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String nombre = snapshot.child("Nombre").getValue().toString();
                    String email = snapshot.child("Correo").getValue().toString();
                    String telefono = snapshot.child("Telefono").getValue().toString();
                    String direccion = snapshot.child("Direccion").getValue().toString();

                    String[] direccionSplit = direccion.split(", Valle del Cauca");

                    tvName.setText("Nombre: " + nombre);
                    tvEmail.setText("Correo: " + email);
                    tvTel.setText("Tel: " + telefono);
                    tvDireccion.setText("Dirección: " + direccionSplit[0]);

                    calificacionEstrellas.getOnRatingBarChangeListener();



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.btnCerrarSesion:

                SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("datos_user", Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                FirebaseAuth.getInstance().signOut();
                goLogin();
                break;

            case R.id.btnConfirmarContrasena:

                contrasena_antigua = etContrasenaActual.getText().toString();
                contrasena_nueva = etNuevaContrasena.getText().toString();

                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();


                if(!contrasena_antigua.isEmpty() && !contrasena_nueva.isEmpty()){
                    if (contrasena_nueva.length() >= 6){
                        if(!contrasena_antigua.equals(contrasena_nueva)){
                            database.child("Users").child("Clientes").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        String contrasena_db = snapshot.child("Contraseña").getValue().toString();
                                        if (contrasena_antigua.equals(contrasena_db)){
                                            Map<String, Object> contraMap = new HashMap<>();
                                            contraMap.put("Contraseña", contrasena_nueva);
                                            database.child("Users").child("Clientes").child(id).updateChildren(contraMap);
                                            //Falta hacer el update en la base de datos local tambien
                                            //Da un pequeño bug que no deja ingresar con la nueva contraseña
                                            //Pero si la cambia en la base detos remota
                                            showAler6();
                                            etContrasenaActual.setText("");
                                            etNuevaContrasena.setText("");
                                        }
                                        else {
                                            showAler5();
                                        }
                                    }
                                    else {
                                        showAler4();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                        else {
                            showAler3();
                        }
                    }
                    else {
                        showAlert2();
                    }
                }else {
                    showAlert1();
                }
                break;
            case  R.id.btnCancelar:

                etContrasenaActual.setText("");
                etNuevaContrasena.setText("");
                break;

            default: break;
        }



    }



    private void goLogin() {
        Intent irHome = new Intent(getActivity(), LoginActivity.class);
        irHome.addFlags(irHome.FLAG_ACTIVITY_CLEAR_TOP | irHome.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(irHome);
    }

    private void showAlert1(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilFragment.super.getContext());
        builder.setTitle("Error");
        builder.setMessage("Los campos de contraseñas no pueden estar vacíos");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlert2(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilFragment.super.getContext());
        builder.setTitle("Error");
        builder.setMessage("La nueva contraseña debe tener al menos 6 caracteres");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAler3(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilFragment.super.getContext());
        builder.setTitle("Error");
        builder.setMessage("La nueva contraseña no puede ser igual a la actual");
        builder.setPositiveButton("Aceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAler4(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilFragment.super.getContext());
        builder.setTitle("Error");
        builder.setMessage("Hubo un problema con la Base de Datos");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAler5(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilFragment.super.getContext());
        builder.setTitle("Error");
        builder.setMessage("La contraseña actual no coincide con la registrada en la Base de Datos");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAler6(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilFragment.super.getContext());
        builder.setTitle("Éxito");
        builder.setMessage("Contraseña actualizada exitosamente");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}