package com.inacap.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    EditText nombre, precio, ingredientes;
    Spinner categoria;
    CheckBox disponibilidad;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nombre = findViewById(R.id.updateNombreProd);
        precio = findViewById(R.id.updatePrecioProd);
        ingredientes = findViewById(R.id.updateIngredientes);
        categoria = findViewById(R.id.updateCategoria);
        disponibilidad = findViewById(R.id.updateDispo);
        button = findViewById(R.id.buttonUpdate);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked =  disponibilidad.isChecked();
                int disponible = 0;
                if (checked) {
                    disponible = 1;
                }

                if (categoria.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar una categoria.",
                            Toast.LENGTH_LONG).show();
                } else if (nombre.length() < 1 || precio.length() < 1 || ingredientes.length() < 1) {
                    Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper mydb = new DatabaseHelper(AddProduct.this);
                    mydb.addProduct(nombre.getText().toString().trim(),
                            Integer.parseInt(precio.getText().toString().trim()), disponible,
                            categoria.getSelectedItem().toString(), ingredientes.getText().toString());
                }

            }
        });
    }
}