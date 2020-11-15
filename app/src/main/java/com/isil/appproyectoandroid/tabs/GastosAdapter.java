package com.isil.appproyectoandroid.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.isil.appproyectoandroid.R;
import com.isil.appproyectoandroid.models.Movimiento;

import java.util.List;

public class GastosAdapter extends BaseAdapter {

    public Context context;
    public int layout;
    public List<Movimiento> gastos;

    public GastosAdapter(Context context, int layout, List<Movimiento> gastos) {
        this.context = context;
        this.layout = layout;
        this.gastos = gastos;
    }

    @Override
    public int getCount() {
        return gastos.size();
    }

    @Override
    public Object getItem(int position) {
        return gastos.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        convertView = layoutInflater.inflate(this.layout, null); // no pasar el viewGroup

        holder = new ViewHolder();
        holder.tvDescripcion = convertView.findViewById(R.id.tvDescripcion);
        holder.tvMonto = convertView.findViewById(R.id.tvMonto);
        holder.tvFecha = convertView.findViewById(R.id.tvFecha);

        final Movimiento movimientoActual = (Movimiento) getItem(posicion);
        holder.tvDescripcion.setText(movimientoActual.getDescripcion());
        float monto = movimientoActual.getMonto();
        holder.tvMonto.setText("S/. " + monto);

        //DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = movimientoActual.getFecha().substring(5, 16);
        holder.tvFecha.setText(date);
        holder.tvMonto.setTextColor(ContextCompat.getColor(context, R.color.gasto));

        return convertView;
    }

    static class ViewHolder {
        private TextView tvDescripcion;
        private TextView tvMonto;
        private TextView tvFecha;
    }
}