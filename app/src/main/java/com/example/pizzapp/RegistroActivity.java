package com.example.pizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {
    
    Button btnRegistrar;
    EditText etEmail, etContrasena, etConfirmarContrasena, etNombre, etTelefono, etDireccion;
    Bundle data = new Bundle();

    //Variables de los datos a ingresar

    String email = "";
    String contrasena = "";
    String confirmarcontrasena = "";
    String nombre = "";
    String telefono = "";
    String direccion = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);

        etEmail = findViewById(R.id.etEmail);
        etContrasena = findViewById(R.id.etContrasena);
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena);
    }

    @Override
    public void onClick(View v) {

        email = etEmail.getText().toString();
        contrasena = etContrasena.getText().toString();
        confirmarcontrasena = etConfirmarContrasena.getText().toString();
        nombre = etNombre.getText().toString();
        telefono = etTelefono.getText().toString();
        direccion = etDireccion.getText().toString();


        if(!email.isEmpty() || !contrasena.isEmpty() || !confirmarcontrasena.isEmpty() || !nombre.isEmpty() || !telefono.isEmpty() || !direccion.isEmpty() ){
            if (contrasena.length() >= 6){
                if(contrasena.equals(confirmarcontrasena)){

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail.getText().toString(),
                            etContrasena.getText().toString()).addOnCompleteListener(this);
                }else{
                    etConfirmarContrasena.setError("Este campo debe ser igual a Contraseña");
                    showAlert2();
                }
            }else{
                etContrasena.setError("Este campo debe contener mínimo 6 caracteres");
                showAlert3();
            }
        }
        else {
            showAlert();
        }
    }



    private void showAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error en el registro de usuario. Hay campos vacios o el usuario ya está registrado");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showAlert2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Las contraseñas no coinciden");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlert3() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("La contraseña debe contener mínimo 6 caracteres");
        builder.setPositiveButton("Aceptar",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void showHomeCliente(String correo_cliente){
        Intent irHome = new Intent(this, ActivityBarCliente.class);
        data.putString("nombre_usuario", etNombre.getText().toString());
        data.putString("telefono_cliente", etTelefono.getText().toString());
        data.putString("direccion_cliente", etDireccion.getText().toString());
        data.putString("correo_cliente", etEmail.getText().toString());
        irHome.putExtras(data);
        irHome.addFlags(irHome.FLAG_ACTIVITY_CLEAR_TOP | irHome.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(irHome);
    }


    public void irInicioSesion(View view) {
        Intent ir = new Intent(this, LoginActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            showHomeCliente(etEmail.getText().toString());
        }else{
            showAlert();
        }
    }
}