package com.hammer67.watsappclone.activities.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.fragments.AjustesFragment;
import com.hammer67.watsappclone.activities.fragments.PerfilFragment;

public class AjustesActivity extends AppCompatActivity {

    private ImageButton imgBack;
    private TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        init();
        setListeners();
        mostrarAjustesFragment(savedInstanceState);
    }

    private void mostrarAjustesFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameAjustes, new AjustesFragment(),AjustesFragment.class.getSimpleName())
                    .commit();
        }
    }

    private void init() {
        this.imgBack = findViewById(R.id.btnBack);
        this.txtTitulo = findViewById(R.id.txtTtulo);
    }

    private void setListeners() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }

    public void cambiarTitulo(String titulo){
        txtTitulo.animate().alpha(0f).setDuration(150).withEndAction(new Runnable() {
            @Override
            public void run() {
                txtTitulo.setText(titulo);
                txtTitulo.animate().alpha(1f).setDuration(150);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }
        else {
            cambiarTitulo("Ajustes");
            PerfilFragment perfilFragment = (PerfilFragment)getSupportFragmentManager()
                    .findFragmentByTag(PerfilFragment.class.getSimpleName());

            if (perfilFragment != null){
                FloatingActionButton floatingActionButton = perfilFragment.getFabCambiarFotoPerfil();
                if (floatingActionButton != null){
                    floatingActionButton.animate().scaleX(0f).scaleY(0f).setDuration(100)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    getSupportFragmentManager().popBackStack();
                                }
                            });
                }
            }
        }
    }
}