package com.hammer67.watsappclone.activities.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.adapters.ViewPagerTitlesAdapters;
import com.hammer67.watsappclone.activities.controlador.MainController;
import com.hammer67.watsappclone.activities.utils.AnimUtils;
import com.hammer67.watsappclone.activities.vistas.cuentas.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPagerTitlesAdapters adapter;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private FloatingActionButton fabMain, fabEditarEstadoMain;
    //////////TOP MENU ///////////////////////////
    private SearchView searchView;
    private Menu menu;
    private boolean searchViewIsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setVPadapter();
        MainController.setCameraIconTabLayout(tabLayout);
        MainController.modificarCameraIconParams(tabLayout);
        setListener();

    }

    private void setVPadapter() {
        this.viewPager.setAdapter(adapter);
        this.viewPager.setOffscreenPageLimit(MainController.getListFragments().size());
        this.viewPager.setCurrentItem(1);
        this.tabLayout.setupWithViewPager(viewPager);
    }

    private void init() {
        this.viewPager = findViewById(R.id.viewPagerMain);
        this.appBarLayout = findViewById(R.id.appBarLayoutMain);
        this.tabLayout = findViewById(R.id.tabLayoutMain);
        this.fabMain = findViewById(R.id.fabMain);
        this.fabEditarEstadoMain = findViewById(R.id.fabEditarEstadoMain);
        this.searchView = findViewById(R.id.searchViewMain);
        this.adapter = new ViewPagerTitlesAdapters(getSupportFragmentManager(), MainController.getListFragments(), MainController.getTitles());
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
    }

    private void setListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                viewPager.setCurrentItem(tabPosition);

                AnimUtils.showFabAddEstado(tabPosition, fabEditarEstadoMain, getBaseContext());

                switch (tabPosition) {
                    case 0:

                        break;

                    case 1:
                        MainController.cambiarFabIcon(R.drawable.ic_chat, fabMain);
                        break;

                    case 2:
                        MainController.cambiarFabIcon(R.drawable.ic_baseline_camera, fabMain);
                        MainController.cambiarFabIcon(R.drawable.ic_edit, fabEditarEstadoMain);
                        break;
                    case 3:
                        MainController.cambiarFabIcon(R.drawable.ic_call_white, fabMain);
                        MainController.cambiarFabIcon(R.drawable.ic_videocam, fabEditarEstadoMain);
                        break;
                }

                MainController.ocultarOpcionesMenu(tabPosition, menu);

                if (searchViewIsVisible) {
                    searchViewIsVisible = false;
                    AnimUtils.hideSearchView(searchView, appBarLayout);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 1:
                        startActivity(new Intent(MainActivity.this, ContactosActivity.class)
                                .putExtra(ContactosActivity.TYPE_ACTION, ContactosActivity.ACTION_CHAT));
                        break;

                    case 2:
                        Toast.makeText(MainActivity.this, "Abrir Camara", Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        startActivity(new Intent(MainActivity.this, ContactosActivity.class)
                                .putExtra(ContactosActivity.TYPE_ACTION, ContactosActivity.ACTION_CALL));
                        break;
                }
            }
        });

        fabEditarEstadoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 2:
                        Toast.makeText(MainActivity.this, "Crear nuevo estado", Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(MainActivity.this, "Crear sala de video", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuBuscar:
                searchViewIsVisible = true;
                appBarLayout.setExpanded(false, true);
                AnimUtils.showSearchView(searchView);
                break;

            case R.id.menuNuevoGrupo:
                Toast.makeText(this, "Nuevo grupo", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuNuevaDifusion:
                Toast.makeText(this, "Nueva difusion", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuWatsAppWebb:
                Toast.makeText(this, "Watsapp Webb", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuMensajesDestacados:
                Toast.makeText(this, "Mensajes destacados", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuAjutes:
                startActivity(new Intent(MainActivity.this, AjustesActivity.class));
                break;

            case R.id.menuSalir:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (searchViewIsVisible) {
            searchViewIsVisible = false;
            AnimUtils.hideSearchView(searchView, appBarLayout);
        } else {
            if (tabLayout.getSelectedTabPosition() == 1) {
                super.onBackPressed();
            } else {
                viewPager.setCurrentItem(1);
            }
        }
    }
}