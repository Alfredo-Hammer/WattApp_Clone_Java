package com.hammer67.watsappclone.activities.vistas.cuentas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.controlador.LoginController;
import com.hammer67.watsappclone.activities.utils.EmailUtils;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRecuperar;
    private ImageView imgLogo;
    private Animation animationLogo;
    private AppCompatButton btnRegistro;
    private Button btnLogin;
    private TextInputEditText txtCorreo, txtContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setListener();

    }

    private void init() {
        this.imgLogo = findViewById(R.id.imgLogo);
        this.tvRecuperar = findViewById(R.id.tvRecuperar);
        this.animationLogo = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_logo);
        this.btnRegistro = findViewById(R.id.btnRegistro);
        this.btnLogin = findViewById(R.id.btnLogin);
        this.txtCorreo = findViewById(R.id.txtCorreoLogin);
        this.txtContrasenia = findViewById(R.id.txtPasswordLogin);
    }

    private void setListener() {
        txtCorreo.addTextChangedListener(textWatcher);
        txtContrasenia.addTextChangedListener(textWatcher);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginController.iniciarSesion(getCorreo(), getContrasenia(),
                        new WeakReference<>(LoginActivity.this),
                        new WeakReference<>(txtContrasenia));
            }
        });

        txtContrasenia.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (puedoIniciarSesion()) {
                        LoginController.iniciarSesion(getCorreo(), getContrasenia(),
                                new WeakReference<>(LoginActivity.this),
                                new WeakReference<>(txtContrasenia));
                    }
                }

                return false;
            }
        });

        tvRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarContraseniaActivity.class);
                startActivity(intent);
            }
        });

        animationLogo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (imgLogo != null && animationLogo != null) {
                            imgLogo.startAnimation(animationLogo);
                        }
                    }
                }, 2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        imgLogo.startAnimation(animationLogo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationLogo != null) {
            animationLogo.cancel();
        }
    }

    public String getCorreo() {
        return txtCorreo.getText().toString();
    }

    public String getContrasenia() {
        return txtContrasenia.getText().toString();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            puedoIniciarSesion();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean puedoIniciarSesion() {
        String correo = getCorreo().trim();
        String contrasenia = getContrasenia().trim();

        if (EmailUtils.esCorreoValido(correo) && contrasenia.length() > 5) {
            btnLogin.setEnabled(true);
            return true;
        } else {
            btnLogin.setEnabled(false);
            return false;
        }
    }
}