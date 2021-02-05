package com.hammer67.watsappclone.activities.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.controlador.FbUser;
import com.hammer67.watsappclone.activities.controlador.PerfilController;
import com.hammer67.watsappclone.activities.models.Usuario;
import com.hammer67.watsappclone.activities.utils.AnimUtils;
import com.hammer67.watsappclone.activities.utils.FirebaseConstants;

import java.lang.ref.WeakReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class PerfilFragment extends Fragment {

    private CircleImageView imgUser;
    private TextView txtNombre, txtEstado;
    private ImageButton btnActualizarNombre, btnActualizarEstado;
    private FloatingActionButton fabCambiarFotoPerfil;
    private ListenerRegistration registrationUserData;


    public PerfilFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        init(view);
        setListeners();
        AnimUtils.scaleFab(fabCambiarFotoPerfil);
        getUserData();
        return view;
    }

    private void setListeners() {
        btnActualizarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getNombre().trim().length() > 0) {
                    PerfilController.actualizarInfoUsuario("nombre", getNombre(), new WeakReference<>(requireContext()));
                } else {
                    Toast.makeText(requireContext(), "Escribir su nombre...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnActualizarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilController.actualizarInfoUsuario("estado", getEstado(), new WeakReference<>(requireContext()));

            }
        });

        fabCambiarFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetOpcionesFoto();
            }
        });
    }

    private void bottomSheetOpcionesFoto() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View layoutbottomSheet = getLayoutInflater().inflate(R.layout.editar_foto_perfil, null);
        setButtomSheetListeners(layoutbottomSheet, bottomSheetDialog);
        bottomSheetDialog.setContentView(layoutbottomSheet);
        bottomSheetDialog.show();
    }

    private void setButtomSheetListeners(View layoutbottomSheet, BottomSheetDialog bottomSheetDialog) {

        layoutbottomSheet.findViewById(R.id.fabEliminarFotoPerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Eliminar foto", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        layoutbottomSheet.findViewById(R.id.fabGalleriaFotoPerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Foto galleria", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        layoutbottomSheet.findViewById(R.id.fabFotoCamaraPerfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Foto camara", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void init(View view) {
        this.imgUser = view.findViewById(R.id.imgUser);
        this.txtNombre = view.findViewById(R.id.txtNombreUsuarioPerfil);
        this.txtEstado = view.findViewById(R.id.txtEstadoUsuarioPerfil);
        this.btnActualizarNombre = view.findViewById(R.id.btnActualizarNombre);
        this.btnActualizarEstado = view.findViewById(R.id.btnActualizarEstado);
        this.fabCambiarFotoPerfil = view.findViewById(R.id.fabCambiarFotoPerfil);

    }

    public FloatingActionButton getFabCambiarFotoPerfil() {
        return fabCambiarFotoPerfil;
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
                        txtEstado.setText(estado);

                        if (!requireActivity().isFinishing()) {
                            Glide.with(requireContext())
                                    .load(imgUrl)
                                    .placeholder(R.color.colorPrimary)
                                    .error(R.drawable.ic_baseline_person_grey)
                                    .into(imgUser);
                        }
                    }
                }


            } catch (NullPointerException | IllegalStateException e) {
                e.getCause();
            }

        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        if (registrationUserData != null) {
            registrationUserData.remove();
            registrationUserData = null;
        }
    }

    public String getNombre() {
        return txtNombre.getText().toString();
    }

    public String getEstado() {
        return txtEstado.getText().toString();
    }
}