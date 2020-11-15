package com.isil.appproyectoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Datos extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "monedero.db";

    public Datos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE movimientos (" +
                "idmovimiento INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "descripcion TEXT, " +
                "monto float, " +
                "movimiento int)"); //1->ingreso   0->gasto
    }

    public long registrarMovimiento(Datos datos, String descripcion, float monto, int movimiento) {
        SQLiteDatabase sqLiteDatabase = datos.getWritableDatabase();
        //sqLiteDatabase.execSQL("any query");

        ContentValues contentValues = new ContentValues();
        contentValues.put("descripcion", descripcion);
        contentValues.put("monto", monto);
        contentValues.put("movimiento", movimiento);

        long nuevoId = sqLiteDatabase.insert("movimientos", null, contentValues);
        return nuevoId;
    }

    public Cursor mostrarTodo(Datos datos) {
        SQLiteDatabase sqLiteDatabase = datos.getReadableDatabase();
        String consultaSQL = "select * from movimientos";
        return sqLiteDatabase.rawQuery(consultaSQL, null);
    }

    public Cursor mostrarIngresos(Datos datos) {
        SQLiteDatabase sqLiteDatabase = datos.getReadableDatabase();
        String consultaSQL = "select * from movimientos where movimiento=?";
        return sqLiteDatabase.rawQuery(consultaSQL, new String[]{"1"});
    }

    public Cursor mostrarGastos(Datos datos) {
        SQLiteDatabase sqLiteDatabase = datos.getReadableDatabase();
        String consultaSQL = "select * from movimientos where movimiento=?";
        return sqLiteDatabase.rawQuery(consultaSQL, new String[]{"-1"});
    }

    public float sumarMontos(Datos datos) {
        float total = 0;
        SQLiteDatabase sqLiteDatabase = datos.getReadableDatabase();
        String consultaSQL = "select sum(monto*movimiento) as monto from movimientos";
        Cursor cursor = sqLiteDatabase.rawQuery(consultaSQL, null);
        if (cursor.moveToFirst()){
            float totals = cursor.getFloat(cursor.getColumnIndex("monto"));
            return totals;
        }
        while (cursor.moveToNext());
        return sumarMontos(datos);
    }

    public void eliminarMovimientos(Datos datos) {
        SQLiteDatabase sqLiteDatabase = datos.getWritableDatabase();
        sqLiteDatabase.delete("movimientos", null, null);
    }

    public void eliminarById(Datos datos, Integer idmovimiento) {
        SQLiteDatabase sqLiteDatabase = datos.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("idmovimiento", idmovimiento);
        sqLiteDatabase.delete("movimientos", "idmovimiento=?", new String[]{String.valueOf(idmovimiento)});
    }

    public Cursor findById(Datos datos, Integer idmovimiento) {
        SQLiteDatabase sqLiteDatabase = datos.getReadableDatabase();
        String consultaSQL = "select * from movimientos where idmovimiento=?";
        return sqLiteDatabase.rawQuery(consultaSQL, new String[]{String.valueOf(idmovimiento)});
    }


    public void updateMovimientos(Datos datos, Integer idmovimiento, String descripcion, float monto, int movimiento) {
        SQLiteDatabase sqLiteDatabase = datos.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put("idmovimiento", idmovimiento);
        args.put("descripcion", descripcion);
        args.put("monto", monto);
        args.put("movimiento", movimiento);
        sqLiteDatabase.update("movimientos", args, "idmovimiento=?", new String[]{String.valueOf(idmovimiento)});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
