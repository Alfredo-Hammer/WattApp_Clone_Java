package com.hammer67.watsappclone.activities.vistas.cuentas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.controlador.RecuperarContrasenia;
import com.hammer67.watsappclone.activities.utils.EmailUtils;

import java.lang.ref.WeakReference;

public class RecuperarContraseniaActivity extends AppCompatActivity {

    private ImageView imageViewBack;
    private Button btnRecuperar;
    private TextInputEditText txtCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasenia);

        imageViewBack = findViewById(R.id.imageViewBack);
        btnRecuperar = findViewById(R.id.btnEnviarCorreo);
        txtCorreo = findViewById(R.id.txtRecuperarContrasenia);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecuperarContrasenia.recuperarContrasenia(getCorreo(),
                        new WeakReference<>(RecuperarContraseniaActivity.this),
                        new WeakReference<>(txtCorreo));
            }
        });

        txtCorreo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                   if (puedoRecuperarContrasenia()){
                       RecuperarContrasenia.recuperarContrasenia(getCorreo(),
                               new WeakReference<>(RecuperarContraseniaActivity.this),
                               new WeakReference<>(txtCorreo));
                   }
                }

                return false;
            }
        });
    }

    public String getCorreo() {
        return txtCorreo.getText().toString();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            puedoRecuperarContrasenia();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean puedoRecuperarContrasenia(){
        String correo = getCorreo().trim();

        if (EmailUtils.esCorreoValido(correo)){
            btnRecuperar.setEnabled(true);
            return true;
        }
        else {
            Toast.makeText(this, "El correo es invalido...", Toast.LENGTH_SHORT).show();
            btnRecuperar.setEnabled(false);
            return false;
        }
    }

}