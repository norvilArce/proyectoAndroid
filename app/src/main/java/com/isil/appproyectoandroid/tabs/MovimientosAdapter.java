package com.isil.appproyectoandroid.tabs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.isil.appproyectoandroid.R;
import com.isil.appproyectoandroid.models.Movimiento;

import java.util.List;

public class MovimientosAdapter extends BaseAdapter {

    public Context context;
    public int layout;
    public List<Movimiento> movimientos;

    public MovimientosAdapter(Context context, int layout, List<Movimiento> movimientos) {
        this.context = context;
        this.layout = layout;
        this.movimientos = movimientos;
    }

    @Override
    public int getCount() {
        return movimientos.size();
    }

    @Override
    public Object getItem(int position) {
        return movimientos.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(this.layout, null); // no pasar el viewGroup

            holder = new ViewHolder();
            holder.tvDescripcion = convertView.findViewById(R.id.tvDescripcion);
            holder.tvMonto = convertView.findViewById(R.id.tvMonto);
            holder.tvFecha = convertView.findViewById(R.id.tvFecha);

            convertView.setTag(holder);
        }       else {

            holder = (ViewHolder)convertView.getTag();
        }

        final Movimiento movimientoActual = (Movimiento) getItem(posicion);
        holder.tvDescripcion.setText(movimientoActual.getDescripcion());
        float monto = movimientoActual.getMonto();
        holder.tvMonto.setText("S/. "+monto);

        //DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = movimientoActual.getFecha();//.substring(5,16);
        holder.tvFecha.setText(date);

        switch (movimientoActual.getMovimiento()){
            case 1:
                holder.tvMonto.setTextColor(ContextCompat.getColor(context,R.color.ingreso));
                break;
            case 0:
                holder.tvMonto.setTextColor(ContextCompat.getColor(context,R.color.gasto));
                break;
            default:
                holder.tvMonto.setTextColor(ContextCompat.getColor(context,R.color.colorText));
        }
        return convertView;
    }

    static class ViewHolder{
        private TextView tvDescripcion;
        private TextView tvMonto;
        private TextView tvFecha;
    }
}