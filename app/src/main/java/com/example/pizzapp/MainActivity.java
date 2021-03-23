package com.example.pizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Conexion conn = new Conexion(this, "pizzApp.db", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();

            if(conn != null){
                Toast.makeText(getApplicationContext(), "CONEXIÓN FALLIDA", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}