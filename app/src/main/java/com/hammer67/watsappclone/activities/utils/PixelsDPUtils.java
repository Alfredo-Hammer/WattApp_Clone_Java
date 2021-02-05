package com.hammer67.watsappclone.activities.utils;

import android.content.Context;

public class PixelsDPUtils {

    public static int convertPixelsToDp(int dp, Context context){
        return (int)(dp * context.getResources().getDisplayMetrics().density);
    }


}
