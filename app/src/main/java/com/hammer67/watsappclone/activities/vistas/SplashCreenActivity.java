package com.hammer67.watsappclone.activities.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hammer67.watsappclone.activities.vistas.cuentas.LoginActivity;

public class SplashCreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            startNewAct(MainActivity.class);
        }
        else {
            startNewAct(LoginActivity.class);
        }
    }

    private void startNewAct(Class clase) {
        startActivity(new Intent(SplashCreenActivity.this,clase));
        finish();
    }
}