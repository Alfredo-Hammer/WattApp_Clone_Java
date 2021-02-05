package com.hammer67.watsappclone.activities.controlador;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.hammer67.watsappclone.activities.models.Usuario;
import com.hammer67.watsappclone.activities.utils.FirebaseConstants;
import com.hammer67.watsappclone.activities.utils.ProgressDialogUtils;
import com.hammer67.watsappclone.activities.vistas.MainActivity;

import java.lang.ref.WeakReference;

public class RegistroController {

    public static void registrarUsuario(String nombre, String correo, String contrasenia,
                                        WeakReference<Activity> activityWeakReference) {

        WeakReference<ProgressDialog> progressDialogWeakReference = ProgressDialogUtils.getDialogWeek(activityWeakReference.get(), null, "Registrando datos...");
        progressDialogWeakReference.get().show();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contrasenia)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            guardarUsuarioEnDatabase(nombre,correo,activityWeakReference,progressDialogWeakReference);

                        } else {
                            Activity activity = activityWeakReference.get();
                            ProgressDialog progressDialog = progressDialogWeakReference.get();
                            if (activity != null && progressDialog != null) {
                                progressDialog.dismiss();
                                Toast.makeText(activity, "No se pudo registrar al usuario...intente de nuevo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    public static void guardarUsuarioEnDatabase(String nombre, String correo, WeakReference<Activity> activityWeakReference, WeakReference<ProgressDialog> progressDialogWeakReference) {

        try {

            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String uid = firebaseUser.getUid();
            long timestampRegistro = firebaseUser.getMetadata().getCreationTimestamp();

            Usuario usuario = new Usuario(uid,nombre,correo,timestampRegistro);

            FirebaseFirestore.getInstance().collection(FirebaseConstants.USERS)
                    .document(uid)
                    .set(usuario, SetOptions.merge())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Activity activity = activityWeakReference.get();
                            ProgressDialog progressDialog = progressDialogWeakReference.get();

                            if (activity != null && progressDialog != null){
                                progressDialog.dismiss();
                                if (task.isSuccessful()){
                                    Toast.makeText(activity, "Registro guardado con exito...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(activity, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    activity.startActivity(intent);
                                }
                                else {
                                    Toast.makeText(activity, "Error al guardar los datos del usuario....", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });



        }catch (NullPointerException e){
            e.getCause();
        }

    }

}
