package com.isil.appproyectoandroid.tabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.isil.appproyectoandroid.AddActivity;
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
        irAAddActivity();
    }

    private void irAAddActivity() {
        startActivity(new Intent(this, AddActivity.class));
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