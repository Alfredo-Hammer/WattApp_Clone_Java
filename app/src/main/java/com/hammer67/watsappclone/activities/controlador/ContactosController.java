package com.hammer67.watsappclone.activities.controlador;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hammer67.watsappclone.activities.utils.FirebaseConstants;

import java.lang.ref.WeakReference;

public class ContactosController {

    public static void getNrContactos(WeakReference<TextView> textViewWeakReference,
                                      WeakReference<Context> contextWeakReference) {

        FirebaseFirestore.getInstance().collection(FirebaseConstants.USERS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        try {
                            if (task.isSuccessful() && task.getResult() != null) {
                                int nrContactos = task.getResult().size() - 1;
                                TextView textView = textViewWeakReference.get();
                                if (textView != null) {
                                    textView.setText(nrContactos+ " Contactos");
                                }
                            } else {
                                Context context = contextWeakReference.get();
                                if (context != null) {
                                    Toast.makeText(context, "Error al intentar obtener numero de usuarios", Toast.LENGTH_SHORT).show();
                                }
                            }


                        } catch (NullPointerException e) {
                            e.getCause();
                        }

                    }
                });
    }

}
