package com.hammer67.watsappclone.activities.vistas.cuentas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.controlador.RegistroController;
import com.hammer67.watsappclone.activities.utils.EmailUtils;

import java.lang.ref.WeakReference;

public class RegisterActivity extends AppCompatActivity {

    private ImageView imageViewBackRegister;
    private TextInputEditText txtNombreRegistro, txtCorreoRegistro, txtContraseniaRegistro, txtConfContrasenia;
    private AppCompatButton btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        startListener();

    }

    private void init() {
        this.imageViewBackRegister = findViewById(R.id.imageViewBackRegister);
        this.txtNombreRegistro = findViewById(R.id.txtNombreRegistro);
        this.txtCorreoRegistro = findViewById(R.id.txtCorreoRegistro);
        this.txtContraseniaRegistro = findViewById(R.id.txtContraseniaRegistro);
        this.txtConfContrasenia = findViewById(R.id.txtConfContrasenia);
        this.btnRegistro = findViewById(R.id.btnRegistro);

    }

    private void startListener() {
        txtNombreRegistro.addTextChangedListener(textWatcher);
        txtCorreoRegistro.addTextChangedListener(textWatcher);
        txtContraseniaRegistro.addTextChangedListener(textWatcher);
        txtConfContrasenia.addTextChangedListener(textWatcher);

        imageViewBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistroController.registrarUsuario(getNombre(),getCorreo(),getContrasenia(), new WeakReference<>(RegisterActivity.this));
            }
        });

        txtConfContrasenia.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    if (puedoRegistrarUsuario()){
                        RegistroController.registrarUsuario(getNombre(),getCorreo(),getContrasenia(), new WeakReference<>(RegisterActivity.this));

                    }
                }

                return false;
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           puedoRegistrarUsuario();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean puedoRegistrarUsuario(){
        String nombre = getNombre().trim();
        String correo = getCorreo().trim();
        String contrasenia = getContrasenia().trim();
        String confContrasenia = getConfContrasenia().trim();

        if (nombre.length() > 2 && EmailUtils.esCorreoValido(correo) && contrasenia.length() > 5 && confContrasenia.equals(contrasenia)){
            btnRegistro.setEnabled(true);
            return true;
        }
        else {
            btnRegistro.setEnabled(false);
            return false;
        }

    }

    public String getNombre() {
        return txtNombreRegistro.getText().toString();
    }

    public String getCorreo() {
        return txtCorreoRegistro.getText().toString();
    }

    public String getContrasenia() {
        return txtContraseniaRegistro.getText().toString();
    }

    public String getConfContrasenia() {
        return txtConfContrasenia.getText().toString();
    }
}