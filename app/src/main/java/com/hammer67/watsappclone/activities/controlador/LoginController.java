package com.hammer67.watsappclone.activities.controlador;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hammer67.watsappclone.activities.utils.ProgressDialogUtils;
import com.hammer67.watsappclone.activities.vistas.MainActivity;

import java.lang.ref.WeakReference;

public class LoginController {

    public static void iniciarSesion(String correo, String contrasenia,
                                     WeakReference<Activity> activityWeakReference,
                                     WeakReference<TextInputEditText> textWeakReference) {

        WeakReference<ProgressDialog> progressDialogWeakReference = ProgressDialogUtils.getDialogWeek(
                activityWeakReference.get(), null, "Iniciando sesion... ");

        progressDialogWeakReference.get().show();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, contrasenia)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Activity activity = activityWeakReference.get();
                        ProgressDialog progressDialog = progressDialogWeakReference.get();

                        if (activity != null && progressDialog != null) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(activity, "Sesion iniciado satisfactoriamente...", Toast.LENGTH_SHORT).show();
                                activity.startActivity(new Intent(activity, MainActivity.class));
                                activity.finish();
                            } else {
                                TextInputEditText txtContrasenia = textWeakReference.get();
                                if (txtContrasenia != null) {
                                    txtContrasenia.setText("");

                                }
                                Toast.makeText(activity, "Error!!, No se pudo iniciar sesion...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
