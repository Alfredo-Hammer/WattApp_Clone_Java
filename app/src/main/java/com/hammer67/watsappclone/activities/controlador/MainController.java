package com.hammer67.watsappclone.activities.controlador;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.fragments.ChatsFragment;
import com.hammer67.watsappclone.activities.fragments.EstadosFragment;
import com.hammer67.watsappclone.activities.fragments.LlamadasFragment;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    public static List<Fragment> getListFragments(){
        List<Fragment> list = new ArrayList<>();
        list.add(new Fragment());
        list.add(new ChatsFragment());
        list.add(new EstadosFragment());
        list.add(new LlamadasFragment());

        return list;
    }

    public static String[] getTitles(){
        return new String[]{"","Chats","Estados","Llamadas"};
    }

    public static void modificarCameraIconParams(TabLayout tabLayout){

        try {
            LinearLayout layout = (LinearLayout)((LinearLayout)tabLayout.getChildAt(0)).getChildAt(0);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
            layoutParams.weight = 0f;
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layout.setLayoutParams(layoutParams);


        }catch (NullPointerException e){
            e.getCause();
        }

    }

    public static void setCameraIconTabLayout(TabLayout tabLayout){
        try {
            tabLayout.getTabAt(0).setIcon(R.drawable.selector_camera);

        }catch (NullPointerException e){
            e.getCause();
        }
    }

    public static void cambiarFabIcon(int resIcon, FloatingActionButton floatingActionButton){
        floatingActionButton.setImageResource(resIcon);

    }

    public static void ocultarOpcionesMenu(int position, Menu menu){
        if (menu != null){
            switch (position){
                case 1:
                    setMenuItemVisible(R.id.menuNuevoGrupo,true,menu);
                    setMenuItemVisible(R.id.menuNuevaDifusion,true,menu);
                    setMenuItemVisible(R.id.menuWatsAppWebb,true,menu);
                    setMenuItemVisible(R.id.menuMensajesDestacados,true,menu);
                    setMenuItemVisible(R.id.menuPrivacidadEstados,false,menu);

                    break;

                case 2:
                    setMenuItemVisible(R.id.menuNuevoGrupo,false,menu);
                    setMenuItemVisible(R.id.menuNuevaDifusion,false,menu);
                    setMenuItemVisible(R.id.menuWatsAppWebb,false,menu);
                    setMenuItemVisible(R.id.menuMensajesDestacados,false,menu);
                    setMenuItemVisible(R.id.menuPrivacidadEstados,true,menu);

                    break;

                case 3:
                    setMenuItemVisible(R.id.menuNuevoGrupo,false,menu);
                    setMenuItemVisible(R.id.menuNuevaDifusion,false,menu);
                    setMenuItemVisible(R.id.menuWatsAppWebb,false,menu);
                    setMenuItemVisible(R.id.menuMensajesDestacados,false,menu);
                    setMenuItemVisible(R.id.menuPrivacidadEstados,false,menu);

                    break;

            }
        }
    }

    public static void setMenuItemVisible(int menuItemId, boolean visible, Menu menu){
        if (menu != null){
            MenuItem menuItem = menu.findItem(menuItemId);
            menuItem.setVisible(visible);
        }

    }

}
