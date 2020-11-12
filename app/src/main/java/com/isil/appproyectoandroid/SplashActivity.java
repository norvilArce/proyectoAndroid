package com.isil.appproyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.isil.appproyectoandroid.tabs.TabsActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mostrarTabsActivity();
            }
        };

        handler.postDelayed(runnable, 4000);
    }

    private void mostrarTabsActivity() {
        Intent intent = new Intent(this, TabsActivity.class);
        startActivity(intent);
        finish();
    }

}