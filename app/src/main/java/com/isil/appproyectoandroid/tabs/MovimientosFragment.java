package com.isil.appproyectoandroid.tabs;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.security.identity.IdentityCredentialStore;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.isil.appproyectoandroid.Datos;
import com.isil.appproyectoandroid.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovimientosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovimientosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tvMonto;
    ArrayList movimientos = new ArrayList<HashMap<String, String>>();
    ListView lvMovimientos;
    MovimientosAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovimientosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovimientosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovimientosFragment newInstance(String param1, String param2) {
        MovimientosFragment fragment = new MovimientosFragment();
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
        View v = inflater.inflate(R.layout.fragment_movimientos, container, false);

        tvMonto = v.findViewById(R.id.tvMonto);
        lvMovimientos = v.findViewById(R.id.lvMovimientos);
        llenarLista();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(lvMovimientos);
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.opt_editar:
                editarMovimiento(info.position);
                return true;
            case R.id.opt_borrar:
                borrarMovimiento(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void editarMovimiento(int position) {
        Object o = movimientos.get(position);
        Toast.makeText(getContext(), o.toString(), Toast.LENGTH_LONG).show();
    }

    private void borrarMovimiento(int position) {

    }

    public void llenarLista() {
        movimientos.clear();
        Datos datos = new Datos(getContext());
        Cursor cursor = datos.mostrarTodo(datos);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("idmovimiento", cursor.getString(cursor.getColumnIndex("idmovimiento")));
                    map.put("fecha", cursor.getString(cursor.getColumnIndex("fecha")));
                    map.put("descripcion", cursor.getString(cursor.getColumnIndex("descripcion")));
                    map.put("monto", cursor.getString(cursor.getColumnIndex("monto")));
                    map.put("movimiento", cursor.getString(cursor.getColumnIndex("movimiento")));
                    movimientos.add(map);
                } while (cursor.moveToNext());

                adapter = new MovimientosAdapter(getActivity(), movimientos);

                lvMovimientos.setAdapter(adapter);
            }
        }
    }

    public void updateMovimientos(){
        adapter.notifyDataSetChanged();
    }
}