package com.isil.appproyectoandroid.tabs;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isil.appproyectoandroid.Datos;
import com.isil.appproyectoandroid.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngresosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngresosFragment extends Fragment {

    ArrayList ingresos = new ArrayList<HashMap<String, String>>();
    ListView lvIngresos;
    TextView tvMonto;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IngresosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngresosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngresosFragment newInstance(String param1, String param2) {
        IngresosFragment fragment = new IngresosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingresos, container, false);
        tvMonto = v.findViewById(R.id.tvMonto);
        lvIngresos = v.findViewById(R.id.lvIngresos);

        llenarLista();

        return v;
    }

    private void llenarLista() {
        ingresos.clear();
        Datos datos = new Datos(getContext());
        Cursor cursor = datos.mostrarIngresos(datos);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("idmovimiento", cursor.getString(cursor.getColumnIndex("idmovimiento")));
                    map.put("fecha", cursor.getString(cursor.getColumnIndex("fecha")));
                    map.put("descripcion", cursor.getString(cursor.getColumnIndex("descripcion")));
                    map.put("monto", cursor.getString(cursor.getColumnIndex("monto")));
                    ingresos.add(map);
                } while (cursor.moveToNext());
                String[] origen = {"fecha", "descripcion", "monto"};
                int[] destino = {R.id.tvFecha, R.id.tvDescripcion, R.id.tvMonto};

                ListAdapter listAdapter = new SimpleAdapter(getContext(),
                        ingresos,
                        R.layout.list_items,
                        origen,
                        destino);
                lvIngresos.setAdapter(listAdapter);
            }
        }
    }
}