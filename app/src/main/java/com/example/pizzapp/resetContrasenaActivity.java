package com.example.pizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetContrasenaActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRestablecerContrasena;
    EditText etEmailRestablecer;

    String emailReset = "";

    FirebaseAuth auth;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_contrasena);
        getSupportActionBar().hide();

        btnRestablecerContrasena = findViewById(R.id.btnRestablecerContrasena);
        btnRestablecerContrasena.setOnClickListener(this);
        etEmailRestablecer = findViewById(R.id.etEmailRestablecer);

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
    }


    public void irLogin(View v){

        Intent ir = new Intent(this, LoginActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);

    }

    @Override
    public void onClick(View v) {
        emailReset = etEmailRestablecer.getText().toString();

        if (!emailReset.isEmpty()){
            dialog.setMessage("Espere un momento... enviando el correo de Restablecer Contraseña");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            resetPassword();
        }
        else {
            etEmailRestablecer.setError("Este campo no puede estar vacío");
        }

    }

    private void resetPassword() {

        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(emailReset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    showAlert2();
                }
                else {
                    showAlert1();
                }
                dialog.dismiss();
            }
        });

    }

    private void showAlert1(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("No se pudo enviar el correo de Restablecer Contraseña. Usuario no registrado ");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlert2(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ËXITO");
        builder.setMessage("El correo de Restablecer Contraseña fue enviado correctamente");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}