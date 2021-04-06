package com.example.pizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    Button btnLogin;
    EditText etEmailLogin, etContrasenaLogin;

    Bundle data;

    //Variables de los datos a ingresar

    String email = "";
    String contrasena = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        etEmailLogin = findViewById(R.id.etEmailLogin);
        etContrasenaLogin = findViewById(R.id.etContrasenaLogin);
    }

    public void irRegistro(View view) {
        Intent ir = new Intent(this, RegistroActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

    @Override
    public void onClick(View v) {

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
    }

    private void showAlert() {
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

    private void showHomeCliente(String correo_cliente){
        Intent irHome = new Intent(this, ActivityBarCliente.class);

        //No se porque no deja colocar estos datos
        //data.putString("correo_cliente", etEmailLogin.getText().toString());
        //irHome.putExtras(data);
        irHome.addFlags(irHome.FLAG_ACTIVITY_CLEAR_TOP | irHome.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(irHome);
    }


    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            showHomeCliente(etEmailLogin.getText().toString());
        }else{
            showAlert();
        }
    }
}