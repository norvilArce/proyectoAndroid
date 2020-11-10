package com.isil.appproyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.isil.appproyectoandroid.tabs.TabsActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnIngresar = (Button)findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        irATabsActivity();
    }

    private void irATabsActivity() {
        startActivity(new Intent(this, TabsActivity.class));
    }
}