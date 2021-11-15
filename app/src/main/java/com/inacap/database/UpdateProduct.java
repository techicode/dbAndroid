package com.inacap.database;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProduct extends AppCompatActivity {

    TextView id;
    EditText nombreUpdate, precioUpdate, ingrdUpdate;
    Spinner cateUpdate;
    CheckBox dispoUpdate;
    Button buttonUpdate, buttonDelete;

    String idtext, nombre, precio, ingrediente, categoria, disponibilidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        nombreUpdate = findViewById(R.id.updateNombreProd);
        precioUpdate = findViewById(R.id.updatePrecioProd);
        ingrdUpdate = findViewById(R.id.updateIngredientes);
        cateUpdate = findViewById(R.id.updateCategoria);
        dispoUpdate = findViewById(R.id.updateDispo);
        id = findViewById(R.id.idText);

        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(nombre);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper mydb = new DatabaseHelper(UpdateProduct.this);
                nombre = nombreUpdate.getText().toString();
                precio = precioUpdate.getText().toString();
                ingrediente = ingrdUpdate.getText().toString();
                categoria = cateUpdate.getSelectedItem().toString();
                if (dispoUpdate.isChecked()) {
                    disponibilidad = "1";
                } else {
                    disponibilidad = "0";
                }
                if (nombre.length() < 1 || precio.length() < 1 || ingrediente.length() < 1) {
                    Toast.makeText(UpdateProduct.this, "Debe rellenar todos los campos.", Toast.LENGTH_SHORT).show();
                } else if (cateUpdate.getSelectedItemPosition() == 0) {
                    Toast.makeText(UpdateProduct.this, "Debe seleccionar una categoria.", Toast.LENGTH_SHORT).show();
                } else {
                     mydb.updateData(idtext, nombre, precio, ingrediente, categoria, disponibilidad);

                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    void getIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") && getIntent().hasExtra("precio") &&
        getIntent().hasExtra("ingredientes") && getIntent().hasExtra("categoria") &&
        getIntent().hasExtra("disponibilidad")) {
            idtext = getIntent().getStringExtra("id");
            nombre = getIntent().getStringExtra("nombre");
            precio = getIntent().getStringExtra("precio");
            ingrediente = getIntent().getStringExtra("ingredientes");
            categoria = getIntent().getStringExtra("categoria");
            disponibilidad = getIntent().getStringExtra("disponibilidad");

            id.setText(idtext);
            nombreUpdate.setText(nombre);
            precioUpdate.setText(precio);
            ingrdUpdate.setText(ingrediente);

            if (categoria.equals("Sandwich")) {
                cateUpdate.setSelection(1);
            } else if (categoria.equals("Pizza")) {
                cateUpdate.setSelection(2);
            } else if (categoria.equals("Combo")) {
                cateUpdate.setSelection(3);
            } else if (categoria.equals("Chorrillana")) {
                cateUpdate.setSelection(4);
            } else {
                cateUpdate.setSelection(0);
            }

            if (disponibilidad.equals("1")) {
                dispoUpdate.setChecked(true);
            }

        } else {
            Toast.makeText(this, "Datos no disponibles.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación de borrado");
        builder.setMessage("Desea borrar el producto " + nombre + "?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateProduct.this);
                databaseHelper.deleteElement(idtext);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}