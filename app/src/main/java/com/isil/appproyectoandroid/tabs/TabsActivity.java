package com.isil.appproyectoandroid.tabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.isil.appproyectoandroid.AddActivity;
import com.isil.appproyectoandroid.Datos;
import com.isil.appproyectoandroid.R;

public class TabsActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tlMovimientos;
    private ViewPager vpTaps;
    private TabItem tabMovimientos, tabIngresos, tabGastos;
    private PagerAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        tlMovimientos = findViewById(R.id.tlMovimientos);
        vpTaps = findViewById(R.id.vpTabs);
        tabIngresos = findViewById(R.id.tabIngreos);
        tabGastos = findViewById(R.id.tabGastos);
        fab = findViewById(R.id.fabAgregarMovimiento);
        adapter = new PagerAdapter(getSupportFragmentManager());

        tlMovimientos.setupWithViewPager(vpTaps);
        adapter.addFragments(new MovimientosFragment(),"Movimientos");
        adapter.addFragments(new IngresosFragment(), "Ingresos");
        adapter.addFragments(new GastosFragment(), "Gastos");
        vpTaps.setAdapter(adapter);

        tlMovimientos.getTabAt(0).setIcon(R.drawable.ic_money);
        tlMovimientos.getTabAt(1).setIcon(R.drawable.ic_ingresos);
        tlMovimientos.getTabAt(2).setIcon(R.drawable.ic_gastos);

        tlMovimientos.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                    case 1:
                    case 2:
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itm_borrar:
                confirmarBorrado();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAgregarMovimiento:
                mostrarAlertDialogAgregar();
                break;
        }
    }

    private void mostrarAlertDialogAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Agregar Movimiento");

        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_agregar, null);
        builder.setView(view);

        final EditText descripcion = (EditText)view.findViewById(R.id.etDescripcion);
        final EditText monto = (EditText)view.findViewById(R.id.etMonto);

        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newDescripcion = descripcion.getText().toString().trim();
                float newMonto = Float.parseFloat(monto.getText().toString().trim());

                if (newDescripcion.length() > 0 && newMonto>0)
                    registrar(newDescripcion, newMonto);
                else
                    Toast.makeText(getApplicationContext(), "No puede ingresar campos vacios", Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void registrar(String descripcion, float monto) {
        Datos datos = new Datos(this);
        int movimiento = 1;
        long autonumerico = datos.registrarMovimiento(datos, descripcion, monto, movimiento);
        Toast.makeText(this, String.valueOf(autonumerico), Toast.LENGTH_SHORT).show();
    }

    private void confirmarBorrado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Movimientos");
        builder.setMessage("¿Estás seguro que quieres eliminar todo los movimientos?");

        builder.setPositiveButton("borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                borrarTodo();
            }
        });
        builder.setNegativeButton("cancelar", null);
        builder.show();
    }

    private void borrarTodo() {
        Toast.makeText(this, "que vas a borrar si no hay nada :v", Toast.LENGTH_SHORT).show();
    }
}