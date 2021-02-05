package com.hammer67.watsappclone.activities.controlador;

import com.google.firebase.auth.FirebaseAuth;

public class FbUser {

    public static String getCurrentUserId (){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
