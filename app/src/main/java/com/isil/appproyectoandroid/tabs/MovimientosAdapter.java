package com.isil.appproyectoandroid.tabs;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.isil.appproyectoandroid.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MovimientosAdapter extends ArrayAdapter {

    Activity activity;
    ArrayList<HashMap<String,String>> maps;

    public MovimientosAdapter(Activity activity, ArrayList<HashMap<String, String>> maps) {
        super(activity, maps.size());
        this.activity = activity;
        this.maps = maps;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = layoutInflater.inflate(R.layout.list_items, null);

        TextView tvDescripcion = rootView.findViewById(R.id.tvDescripcion);
        TextView tvMonto = rootView.findViewById(R.id.tvMonto);
        TextView tvFecha = rootView.findViewById(R.id.tvFecha);

        HashMap<String,String> map = maps.get(position);

        tvDescripcion.setText(map.get("descripcion"));
        float monto = Float.parseFloat(map.get("monto"));
        tvMonto.setText("S/. "+monto);

        //DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = map.get("fecha").substring(5,16);
        tvFecha.setText(date);

        Log.d("valormov", map.get("movimiento"));

        //int movimiento = Integer.parseInt(map.get("movimiento"));
        switch (map.get("movimiento")){
            case "1":
                tvMonto.setTextColor(ContextCompat.getColor(getContext(),R.color.ingreso));
                break;
            case "0":
                tvMonto.setTextColor(ContextCompat.getColor(getContext(),R.color.gasto));
                break;
            default:
                tvMonto.setTextColor(ContextCompat.getColor(getContext(),R.color.colorText));
        }


        return rootView;
    }

    @Override
    public int getCount() {
        return maps.size();
    }
}