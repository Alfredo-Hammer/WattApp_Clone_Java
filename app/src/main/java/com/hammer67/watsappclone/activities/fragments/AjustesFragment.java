package com.hammer67.watsappclone.activities.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionSet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.controlador.FbUser;
import com.hammer67.watsappclone.activities.models.Usuario;
import com.hammer67.watsappclone.activities.utils.FirebaseConstants;
import com.hammer67.watsappclone.activities.utils.TxtUtils;
import com.hammer67.watsappclone.activities.vistas.AjustesActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class AjustesFragment extends Fragment {

    private CircleImageView imgUser;
    private TextView txtNombre, txtEstado;
    private AppCompatButton btnCuenta, btnChats, btnNotificaciones, btnDatosAlmacenamiento, btnAyuda;
    private ListenerRegistration registrationUserData;

    public AjustesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);
        init(view);
        cambiarBtnTxt();
        setListeners(view);
        getUserData();
        return view;

    }

    private void setListeners(View view) {
        view.findViewById(R.id.layoutDatosUsuario).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarTitulo();
                mostrarPerfilFragment();
            }
        });
    }

    private void mostrarPerfilFragment() {
        try {
            PerfilFragment perfilFragment = new PerfilFragment();
            perfilFragment.setSharedElementEnterTransition(getEnterTransitionPerfil());

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.addSharedElement(imgUser, imgUser.getTransitionName());
            fragmentTransaction.replace(R.id.frameAjustes, perfilFragment, perfilFragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(perfilFragment.getClass().getName());
            fragmentTransaction.commit();


        } catch (NullPointerException e) {
            e.getCause();
        }
    }

    private TransitionSet getEnterTransitionPerfil() {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move));
        transitionSet.setDuration(300);
        transitionSet.setStartDelay(150);
        return transitionSet;
    }

    private void cambiarTitulo() {
        AjustesActivity activity = (AjustesActivity) getActivity();
        if (activity != null) {
            activity.cambiarTitulo("Perfil");
        }
    }

    private void cambiarBtnTxt() {
        TxtUtils.setTitleSubTitleButton("Cuenta\n", "Privacidad,seguridad, cambiar numero", btnCuenta);
        TxtUtils.setTitleSubTitleButton("Chats\n", "Tema,fondo de pantalla, historial de chats", btnChats);
        TxtUtils.setTitleSubTitleButton("Notificaciones\n", "Tono de mensajes, grupo y llamadas", btnNotificaciones);
        TxtUtils.setTitleSubTitleButton("Datos y almacenamiento\n", "Uso de red, descarga automatica", btnDatosAlmacenamiento);
        TxtUtils.setTitleSubTitleButton("Ayuda\n", "Preguntas frecuentes, contactanos, politicas", btnAyuda);
    }

    private void init(View view) {
        this.imgUser = view.findViewById(R.id.imgUser);
        this.txtNombre = view.findViewById(R.id.txtNombreUser);
        this.txtEstado = view.findViewById(R.id.txtEstadoUser);
        this.btnCuenta = view.findViewById(R.id.btnAjusteCuenta);
        this.btnChats = view.findViewById(R.id.btnAjusteChats);
        this.btnNotificaciones = view.findViewById(R.id.btnAjusteNotificaciones);
        this.btnDatosAlmacenamiento = view.findViewById(R.id.btnAjusteDatosAlmacenamiento);
        this.btnAyuda = view.findViewById(R.id.btnAjusteAyuda);
    }

    private void getUserData() {
        DocumentReference documentReference = FirebaseFirestore.getInstance()
                .collection(FirebaseConstants.USERS)
                .document(FbUser.getCurrentUserId());
        registrationUserData = documentReference.addSnapshotListener(userDataListener);
    }

    private EventListener<DocumentSnapshot> userDataListener = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
            try {
                if (documentSnapshot != null) {
                    Usuario usuario = documentSnapshot.toObject(Usuario.class);

                    if (usuario != null) {
                        String imgUrl = usuario.getImageUrl();
                        String nombre = usuario.getNombre();
                        String estado = usuario.getEstado();

                        txtNombre.setText(nombre);


                        if (!requireActivity().isFinishing()) {
                            Glide.with(requireContext())
                                    .load(imgUrl)
                                    .placeholder(R.color.colorPrimary)
                                    .error(R.drawable.ic_baseline_person_grey)
                                    .into(imgUser);
                        }

                        if (estado != null && estado.length() > 0) {
                            txtEstado.setText(estado);
                            txtEstado.setVisibility(View.VISIBLE);
                        } else {
                            txtEstado.setVisibility(View.GONE);

                        }
                    }
                }


            } catch (NullPointerException | IllegalStateException e) {
                e.getCause();
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (registrationUserData != null) {
            registrationUserData.remove();
            registrationUserData = null;
        }
    }
}