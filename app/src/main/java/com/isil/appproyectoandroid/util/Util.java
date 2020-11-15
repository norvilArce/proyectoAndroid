package com.isil.appproyectoandroid.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.isil.appproyectoandroid.ModificarActivity;
import com.isil.appproyectoandroid.models.Movimiento;

public class Util {
    public void editarMovimiento(Movimiento movimiento, Context context) {
        Intent intent = new Intent(context, ModificarActivity.class);
        intent.putExtra("id", movimiento.getIdmovimiento());
        intent.putExtra("descripcion", movimiento.getDescripcion());
        intent.putExtra("monto", movimiento.getMonto());
        context.startActivity(intent);
        //Toast.makeText(context, "asi que queires editar: "+descripcion, Toast.LENGTH_LONG).show();
    }

    public void borrarMovimiento(Movimiento movimiento, Context context) {
        String descripcion = movimiento.getDescripcion();
        Toast.makeText(context, "asi que queires borrar: "+descripcion, Toast.LENGTH_LONG).show();
    }
}
