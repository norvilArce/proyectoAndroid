package com.isil.appproyectoandroid.tabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.isil.appproyectoandroid.Datos;
import com.isil.appproyectoandroid.ModificarActivity;
import com.isil.appproyectoandroid.R;
import com.isil.appproyectoandroid.models.Movimiento;
import com.isil.appproyectoandroid.util.Util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngresosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngresosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Movimiento> ingresos = new ArrayList<>();
    private ListView lvIngresos;
    IngresosAdapter adapter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingresos, container, false);
        lvIngresos = v.findViewById(R.id.lvIngresos);
        llenarLista();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new IngresosAdapter(getActivity(), R.layout.list_items, ingresos);
        lvIngresos.setAdapter(adapter);
        registerForContextMenu(lvIngresos);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_ing, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.opt_editar:
                editarMovimiento(ingresos.get(info.position));
                return true;
            case R.id.opt_borrar:
                borrarMovimiento(ingresos.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void borrarMovimiento(Movimiento ingreso) {
        Integer idmovimiento = ingreso.getIdmovimiento();
        Datos datos = new Datos(getContext());
        datos.eliminarById(datos, idmovimiento);
        llenarLista();
        adapter.notifyDataSetChanged();
        TabsActivity.calcularSaldo(getContext(),getActivity());
    }

    private void editarMovimiento(Movimiento ingreso) {
        Intent intent = new Intent(getContext(), ModificarActivity.class);
        intent.putExtra("id", ingreso.getIdmovimiento());
        intent.putExtra("descripcion", ingreso.getDescripcion());
        intent.putExtra("monto", ingreso.getMonto());
        intent.putExtra("movimiento", ingreso.getMovimiento());
        getContext().startActivity(intent);
    }

    public void llenarLista() {
        ingresos.clear();
        Datos datos = new Datos(getContext());
        Cursor cursor = datos.mostrarIngresos(datos);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Movimiento gasto;
                    Integer idmovimiento = cursor.getInt(cursor.getColumnIndex("idmovimiento"));
                    String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                    String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                    float monto =cursor.getFloat(cursor.getColumnIndex("monto"));
                    int tipo = cursor.getInt(cursor.getColumnIndex("movimiento"));
                    gasto = new Movimiento(idmovimiento, fecha, descripcion, monto, tipo);
                    ingresos.add(gasto);
                } while (cursor.moveToNext());

            }
        }
    }
}