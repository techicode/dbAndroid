package com.inacap.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList id, nombre, precio, ingredientes, categoria, disponibilidad;


    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList nombre, ArrayList precio,
                  ArrayList ingredientes, ArrayList categoria, ArrayList disponibilidad) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.categoria = categoria;
        this.disponibilidad = disponibilidad;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listar_productos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.idTxt.setText(String.valueOf(id.get(position)));
        holder.nombreTxt.setText(String.valueOf(nombre.get(position)));
        holder.precioTxt.setText(String.valueOf(precio.get(position)));
        holder.ingredienteTxt.setText(String.valueOf(ingredientes.get(position)));
        holder.catTxt.setText(String.valueOf(categoria.get(position)));
        holder.dispTxt.setText(String.valueOf(disponibilidad.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateProduct.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("nombre", String.valueOf(nombre.get(position)));
                intent.putExtra("precio", String.valueOf(precio.get(position)));
                intent.putExtra("ingredientes", String.valueOf(ingredientes.get(position)));
                intent.putExtra("categoria", String.valueOf(categoria.get(position)));
                intent.putExtra("disponibilidad", String.valueOf(disponibilidad.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idTxt, nombreTxt, precioTxt, ingredienteTxt, catTxt, dispTxt;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idTxt = itemView.findViewById(R.id.textID);
            nombreTxt = itemView.findViewById(R.id.textNombreProd);
            precioTxt = itemView.findViewById(R.id.textPrecio);
            ingredienteTxt = itemView.findViewById(R.id.textIngredientes);
            catTxt = itemView.findViewById(R.id.textCat);
            dispTxt = itemView.findViewById(R.id.textDispo);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
