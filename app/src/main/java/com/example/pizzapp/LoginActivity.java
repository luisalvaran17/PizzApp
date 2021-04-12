package com.example.pizzapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {


    Button btnLogin, btnGoogle;
    EditText etEmailLogin, etContrasenaLogin;

    LinearLayout loginLayout;
    DatabaseReference database;
    public PizzAppDB db;

    //Variables de los datos a ingresar

    String email = "";
    String contrasena = "";

    static final int SIGN_IN_CODE = 202;

    String address = "Cra. 18 #30-117 a 30-1";
    String passw = "1234567";
    String phone = "31576320";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance().getReference();

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        etEmailLogin = findViewById(R.id.etEmailLogin);
        etContrasenaLogin = findViewById(R.id.etContrasenaLogin);

        loginLayout = findViewById(R.id.loginLayout);

        //Google
        btnGoogle = findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(this);


        SharedPreferences preferences = getSharedPreferences("datos_user", Context.MODE_PRIVATE);
        String user = preferences.getString("user", null);

    }



    public void irRegistro(View view) {
        Intent ir = new Intent(this, RegistroActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnLogin:
                email = etEmailLogin.getText().toString();
                contrasena = etContrasenaLogin.getText().toString();

                if (!email.isEmpty() && !contrasena.isEmpty()){

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmailLogin.getText().toString(),
                            etContrasenaLogin.getText().toString()).addOnCompleteListener(this);
                }
                //Pequeño bug: se cierra cuando se le mandan datos con Bundle aquí y en Register
                else {
                    etEmailLogin.setError("Este campo no puede estar vacío");
                    etContrasenaLogin.setError("Este campo no puede estar vacío");
                    showAlert2();
                }
                break;

            case R.id.btnGoogle:

                GoogleSignInOptions googleConf  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
// Configure Google Sign In
                GoogleSignInClient googleClient =  GoogleSignIn.getClient(this, googleConf);
                googleClient.signOut();

                startActivityForResult(googleClient.getSignInIntent(), SIGN_IN_CODE);
                break;


            default: break;
        }


    }

    private void showAlert1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Usuario no registrado");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlert2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Hay campos vacios");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlert3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("No se pudo Iniciar Sesión con Google");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showHomeCliente(String correo_cliente){
        Intent irHome = new Intent(this, ActivityBarCliente.class);
        irHome.addFlags(irHome.FLAG_ACTIVITY_CLEAR_TOP | irHome.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(irHome);
    }

    private void showHomeRepartidor(String correo_repartidor){
        Intent irHome = new Intent(this, ActivityBarRepartidor.class);
        irHome.addFlags(irHome.FLAG_ACTIVITY_CLEAR_TOP | irHome.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(irHome);
    }


    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            database.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){

                        if (snapshot.child("Clientes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Correo").getValue() != null) {
                            String correo_cliente = snapshot.child("Clientes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Correo").getValue().toString();

                            if (correo_cliente.equals(email)) {
                                showHomeCliente(etEmailLogin.getText().toString());
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuario Cliente no registrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (snapshot.child("Repartidores").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Correo").getValue() != null) {
                            String correo_repartidor = snapshot.child("Repartidores").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Correo").getValue().toString();
                            System.out.println("entra");
                            if (correo_repartidor.equals(email)) {
                                showHomeRepartidor(etEmailLogin.getText().toString());
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuario Repartidor no registrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            // showHomeCliente(etEmailLogin.getText().toString());
        }else{
            showAlert1();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if (account != null){

                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Map<String, Object> map = new HashMap<>();
                                map.put("Nombre", account.getDisplayName());
                                map.put("Correo", account.getEmail());
                                map.put("Telefono", phone);
                                map.put("Direccion", address);
                                map.put("Contraseña", passw);

                                db = new PizzAppDB(LoginActivity.this);

                                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                database.child("Users").child("Clientes").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task2) {
                                        if (task2.isSuccessful()){
                                            db.insertUsuario(id, account.getDisplayName(), passw, account.getEmail());
                                            db.insertCliente(id, address, phone);
                                            showHomeCliente(account.getEmail());
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "I D K", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                
                                
                            }
                            else {
                                showAlert3();
                            }
                        }
                    });
                }

            } catch (ApiException e) {
                showAlert3();
            }

        }
    }

    public void irResetContrasena(View view) {
        Intent irHome = new Intent(this, resetContrasenaActivity.class);
        irHome.addFlags(irHome.FLAG_ACTIVITY_CLEAR_TOP | irHome.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(irHome);
    }
}