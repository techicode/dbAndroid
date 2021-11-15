package com.inacap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "StartProductos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "productos";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE_PROD = "nombre_producto";
    private static final String COLUMN_PRECIO = "precio_producto";
    private static final String COLUMN_DISPONIBILIDAD = "dispo_producto";
    private static final String COLUMN_CATEGORIA = "cat_producto";
    private static final String COLUMN_INGREDIENTES = "ingr_producto";

    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, " + COLUMN_NOMBRE_PROD + " TEXT, " + COLUMN_PRECIO + " INTEGER, "
                + COLUMN_DISPONIBILIDAD + " INTEGER, " + COLUMN_CATEGORIA + " TEXT, " +
                COLUMN_INGREDIENTES + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addProduct(String nombre, int precio, int disponibilidad, String categoria, String ingredientes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOMBRE_PROD, nombre);
        cv.put(COLUMN_PRECIO, precio);
        cv.put(COLUMN_DISPONIBILIDAD, disponibilidad);
        cv.put(COLUMN_CATEGORIA, categoria);
        cv.put(COLUMN_INGREDIENTES, ingredientes);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Error al ingresar el producto.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Agregado con éxito.", Toast.LENGTH_LONG).show();
        }
    }

    Cursor leerAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String idup, String nombre, String precio, String ingredientes, String categoria, String disponibilidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOMBRE_PROD, nombre);
        cv.put(COLUMN_PRECIO, precio);
        cv.put(COLUMN_INGREDIENTES, ingredientes);
        cv.put(COLUMN_CATEGORIA, categoria);
        cv.put(COLUMN_DISPONIBILIDAD, disponibilidad);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{idup});
        if (result == -1) {
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteElement(String iddel) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{iddel});
        if (result == -1) {
            Toast.makeText(context, "Error al borrar.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Borrado con éxito.", Toast.LENGTH_SHORT).show();
        }
    }
}
