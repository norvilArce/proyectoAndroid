package com.isil.appproyectoandroid.tabs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.isil.appproyectoandroid.Datos;
import com.isil.appproyectoandroid.R;

public class TabsActivity extends AppCompatActivity implements View.OnClickListener {

    static final float montoInicial = 0;
    private static TextView tvSaldo;
    MovimientosFragment movimientosFragment = new MovimientosFragment();
    IngresosFragment ingresosFragment = new IngresosFragment();
    GastosFragment gastosFragment = new GastosFragment();
    private TabLayout tlMovimientos;
    private ViewPager vpTaps;
    private TabItem tabIngresos, tabGastos;
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
        tvSaldo = findViewById(R.id.tvSaldo);
        adapter = new PagerAdapter(getSupportFragmentManager());

        getSupportActionBar().setIcon(R.mipmap.ic_billetes);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calcularSaldo(this, this);

        tlMovimientos.setupWithViewPager(vpTaps);
        adapter.addFragments(movimientosFragment, "Movimientos");
        adapter.addFragments(ingresosFragment, "Ingresos");
        adapter.addFragments(gastosFragment, "Gastos");
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
        switch (item.getItemId()) {
            case R.id.itm_borrar:
                confirmarBorrado();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabAgregarMovimiento:
                mostrarAlertDialogAgregar();
                break;
        }
    }

    public static void calcularSaldo(Context context, Activity activity) {
        Datos datos = new Datos(context);
        float v = datos.sumarMontos(datos);
        float saldo = montoInicial + v;
        tvSaldo.setText(saldo + "");
        activity.setTitle(" "+saldo);
    }

    private void mostrarAlertDialogAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(Html.fromHtml("<font color='#c7a500'>Añadir Movimiento</font>"));

        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_agregar, null);
        builder.setView(view);

        final EditText etDescripcion = view.findViewById(R.id.etDescripcion);
        final EditText etMonto = view.findViewById(R.id.etMonto);
        final RadioButton rbIngreso = view.findViewById(R.id.rbIngreso);
        final RadioButton rbGasto = view.findViewById(R.id.rbGasto);

        rbIngreso.setChecked(true);
        builder.setPositiveButton(Html.fromHtml("<font color='#c7a500'>añadir</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String descripcion = etDescripcion.getText().toString().trim();
                String monto = etMonto.getText().toString().trim();

                if (!descripcion.equals("") && !monto.equals("")) {
                    float fMonto = Float.parseFloat(monto);
                    int movimiento;
                    if (!rbGasto.isChecked()) {
                        movimiento = 1;
                    } else {
                        movimiento = -1;
                    }
                    registrar(descripcion, fMonto, movimiento);
                } else {
                    Toast.makeText(getApplicationContext(), "No puede ingresar campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(Html.fromHtml("<font color='#c7a500'>cancelar</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
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

    public void registrar(String descripcion, float monto, int movimiento) {
        Datos datos = new Datos(this);
        long autonumerico = datos.registrarMovimiento(datos, descripcion, monto, movimiento);
        Toast.makeText(this, String.valueOf(autonumerico), Toast.LENGTH_SHORT).show();

        if (movimiento == -1) {
            gastosFragment.llenarLista();
        } else if (movimiento == 1) {
            ingresosFragment.llenarLista();
        }
        movimientosFragment.llenarLista();
        calcularSaldo(this, this);
    }

    private void borrarTodo() {
        Datos datos = new Datos(this);
        datos.eliminarMovimientos(datos);
        movimientosFragment.llenarLista();
        movimientosFragment.adapter.notifyDataSetChanged();
        ingresosFragment.llenarLista();
        ingresosFragment.adapter.notifyDataSetChanged();
        gastosFragment.llenarLista();
        gastosFragment.adapter.notifyDataSetChanged();
        calcularSaldo(this, this);
        Toast.makeText(this, "Movimientos elimninados", Toast.LENGTH_SHORT).show();
    }
}