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

        PizzAppDB inveDB = new PizzAppDB(this);
        SQLiteDatabase db = inveDB.getWritableDatabase();
        inveDB.onCreate(db);

        Toast.makeText(getApplicationContext(), "Creando", Toast.LENGTH_SHORT).show();

    }
}