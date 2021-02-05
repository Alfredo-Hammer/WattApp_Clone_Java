package com.hammer67.watsappclone.activities.controlador;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.hammer67.watsappclone.activities.utils.ProgressDialogUtils;

import java.lang.ref.WeakReference;

public class RecuperarContrasenia {

    public static void recuperarContrasenia(String correo, WeakReference<Activity> activityWeakReference,
                                            WeakReference<TextInputEditText> textWeakReference) {

        WeakReference<ProgressDialog> progressDialogWeakReference = ProgressDialogUtils.getDialogWeek(
                activityWeakReference.get(), null, "Recuperando contraseña...");

        progressDialogWeakReference.get().show();

        FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Activity activity = activityWeakReference.get();
                        TextInputEditText txtCorreo = textWeakReference.get();
                        ProgressDialog progressDialog = progressDialogWeakReference.get();

                        if (activity != null && correo != null && progressDialog != null) {
                            progressDialog.dismiss();
                            txtCorreo.setText("");

                            if (task.isSuccessful()) {
                                Toast.makeText(activity, "Se ha enviado un correo para poder que puedas cambiar la contraseña", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(activity, "Error!! no se pudo enviar el correo de recuperacion...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }


}
