package com.isil.appproyectoandroid.tabs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.isil.appproyectoandroid.Datos;
import com.isil.appproyectoandroid.ModificarActivity;
import com.isil.appproyectoandroid.R;
import com.isil.appproyectoandroid.models.Movimiento;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GastosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GastosFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GastosAdapter adapter;
    private List<Movimiento> gastos = new ArrayList();
    private ListView lvGastos;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GastosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GastosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GastosFragment newInstance(String param1, String param2) {
        GastosFragment fragment = new GastosFragment();
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
        View v = inflater.inflate(R.layout.fragment_gastos, container, false);

        lvGastos = v.findViewById(R.id.lvGastos);


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new GastosAdapter(getActivity(), R.layout.list_items, gastos);
        lvGastos.setAdapter(adapter);
        llenarLista();
        registerForContextMenu(lvGastos);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.opt_editar:
                editarMovimiento(gastos.get(info.position));
                return true;
            case R.id.opt_borrar:
                borrarMovimiento(gastos.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void borrarMovimiento(Movimiento gasto) {
        Integer idmovimiento = gasto.getIdmovimiento();
        Datos datos = new Datos(getContext());
        datos.eliminarById(datos, idmovimiento);
        llenarLista();
        adapter.notifyDataSetChanged();
        TabsActivity.calcularSaldo(getActivity());
    }

    private void editarMovimiento(Movimiento gasto) {
        Intent intent = new Intent(getContext(), ModificarActivity.class);
        intent.putExtra("id", gasto.getIdmovimiento());
        intent.putExtra("descripcion", gasto.getDescripcion());
        intent.putExtra("monto", gasto.getMonto());
        intent.putExtra("movimiento", gasto.getMovimiento());
        getContext().startActivity(intent);
    }

    public void llenarLista() {
        gastos.clear();
        Datos datos = new Datos(getContext());
        Cursor cursor = datos.mostrarGastos(datos);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Integer idmovimiento = cursor.getInt(cursor.getColumnIndex("idmovimiento"));
                    String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                    String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                    float monto = cursor.getFloat(cursor.getColumnIndex("monto"));
                    int tipo = cursor.getInt(cursor.getColumnIndex("movimiento"));
                    Movimiento gasto = new Movimiento(idmovimiento, fecha, descripcion, monto, tipo);
                    gastos.add(gasto);
                } while (cursor.moveToNext());
            }
        }
    }
}