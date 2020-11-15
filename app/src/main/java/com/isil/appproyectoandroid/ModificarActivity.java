package com.isil.appproyectoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.isil.appproyectoandroid.tabs.TabsActivity;

public class ModificarActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etDescripcion, etMonto;
    Button btnActualizar, btnCancelar;
    Integer idmovimiento;
    String descripcion;
    float monto;
    int movimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        etDescripcion = findViewById(R.id.etDescripcion);
        etMonto = findViewById(R.id.etMonto);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnCancelar = findViewById(R.id.btnCancelar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idmovimiento = extras.getInt("id");
            descripcion = extras.getString("descripcion");
            monto = extras.getFloat("monto");
            movimiento = extras.getInt("movimiento");

            etDescripcion.setText(descripcion);
            etMonto.setText("" + monto);
        }

        btnActualizar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.btnActualizar:
                String des = etDescripcion.getText().toString();
                String mon = etMonto.getText().toString();
                actualizarMovimiento();
                this.returnHome();
                break;

            case R.id.btnCancelar:
                this.returnHome();
                break;
        }
    }

    private void actualizarMovimiento() {
        Datos datos = new Datos(this);
        descripcion = etDescripcion.getText().toString();
        monto = Float.parseFloat(etMonto.getText().toString());
        datos.updateMovimientos(datos, idmovimiento, descripcion, monto, movimiento);
    }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                TabsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }
}