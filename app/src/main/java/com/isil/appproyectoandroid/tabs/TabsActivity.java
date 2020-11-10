package com.isil.appproyectoandroid.tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.isil.appproyectoandroid.AddActivity;
import com.isil.appproyectoandroid.R;

public class TabsActivity extends AppCompatActivity implements View.OnClickListener {

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
        adapter = new PagerAdapter(getSupportFragmentManager());

        tlMovimientos.setupWithViewPager(vpTaps);
        adapter.addFragments(new IngresosFragment(), "Ingresos");
        adapter.addFragments(new GastosFragment(), "Gastos");
        vpTaps.setAdapter(adapter);

        tlMovimientos.getTabAt(0).setIcon(R.drawable.ic_ingresos);
        tlMovimientos.getTabAt(1).setIcon(R.drawable.ic_gastos);

        tlMovimientos.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                    case 1:
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
    public void onClick(View view) {
        irAAddActivity();
    }

    private void irAAddActivity() {
        startActivity(new Intent(this, AddActivity.class));
    }
}