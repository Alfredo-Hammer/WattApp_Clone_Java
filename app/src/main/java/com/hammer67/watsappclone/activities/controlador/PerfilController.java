package com.hammer67.watsappclone.activities.controlador;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.hammer67.watsappclone.activities.utils.FirebaseConstants;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class PerfilController {

    public static void actualizarInfoUsuario(String key, String value, WeakReference<Context> contextWeakReference){

        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);

        FirebaseFirestore.getInstance().collection(FirebaseConstants.USERS)
                .document(FbUser.getCurrentUserId())
                .set(map, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Context context = contextWeakReference.get();
                        if (context != null){
                            if (task.isSuccessful()){
                                Toast.makeText(context, "Informacion actualizada correctamente", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "Error!! no se pudo actualizar los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}
