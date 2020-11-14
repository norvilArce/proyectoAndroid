package com.isil.appproyectoandroid.tabs;

import android.database.Cursor;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.isil.appproyectoandroid.Datos;
import com.isil.appproyectoandroid.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GastosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GastosFragment extends Fragment {


    ArrayList gastos = new ArrayList<HashMap<String, String>>();
    ListView lvGastos;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        llenarLista();

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(lvGastos);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.opt_editar:
                editarMovimiento();
                return true;
            case R.id.opt_borrar:
                borrarMovimiento();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void editarMovimiento() {

    }

    private void borrarMovimiento() {

    }

    public void llenarLista() {
        gastos.clear();
        Datos datos = new Datos(getContext());
        Cursor cursor = datos.mostrarGastos(datos);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("idmovimiento", cursor.getString(cursor.getColumnIndex("idmovimiento")));
                    map.put("fecha", cursor.getString(cursor.getColumnIndex("fecha")));
                    map.put("descripcion", cursor.getString(cursor.getColumnIndex("descripcion")));
                    map.put("monto", cursor.getString(cursor.getColumnIndex("monto")));
                    map.put("movimiento", cursor.getString(cursor.getColumnIndex("movimiento")));
                    gastos.add(map);
                } while (cursor.moveToNext());

                MovimientosAdapter adapter = new MovimientosAdapter(getActivity(), gastos);
                lvGastos.setAdapter(adapter);
            }
        }
    }
}