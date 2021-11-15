package com.inacap.database;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;

    DatabaseHelper dbHelper;
    ArrayList<String> id, nombre, precio, ingredientes, categoria, disponibilidad;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.vistaRecycle);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProduct.class);
                startActivity(intent);
            }
        });

        dbHelper = new DatabaseHelper(MainActivity.this);
        id = new ArrayList<>();
        nombre = new ArrayList<>();
        precio = new ArrayList<>();
        ingredientes = new ArrayList<>();
        categoria = new ArrayList<>();
        disponibilidad = new ArrayList<>();

        cargarArray();
        customAdapter = new CustomAdapter(MainActivity.this, this, id, nombre, precio, ingredientes,
                categoria, disponibilidad);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void cargarArray() {
        Cursor cursor = dbHelper.leerAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay elementos en la base de datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                nombre.add(cursor.getString(1));
                precio.add(cursor.getString(2));
                disponibilidad.add(cursor.getString(3));
                categoria.add(cursor.getString(4));
                ingredientes.add(cursor.getString(5));
            }
        }
    }
}