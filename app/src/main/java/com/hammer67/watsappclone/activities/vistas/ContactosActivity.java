package com.hammer67.watsappclone.activities.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.adapters.ContactosAdapter;
import com.hammer67.watsappclone.activities.controlador.ContactosController;
import com.hammer67.watsappclone.activities.controlador.FbUser;
import com.hammer67.watsappclone.activities.models.Usuario;
import com.hammer67.watsappclone.activities.utils.FirebaseConstants;

import java.lang.ref.WeakReference;
import java.util.List;

public class ContactosActivity extends AppCompatActivity {

    public static final String TYPE_ACTION = "KeyTypeAction";
    public static final String ACTION_CHAT = "CHAT";
    public static final String ACTION_CALL = "CALL";
    public static final String ACTION_SEND_MESSAGE = "SEND_MESSAGE";

    private ContactosAdapter contactosAdapter;
    private String typeAction;
    private RecyclerView recyclerViewContactos;
    private TextView textNrContactos;
    private ImageButton btnBack;
    private DocumentSnapshot lastDocumentVisible;
    private boolean isLoading = false, isLastPage = false;
    private final int PAGE_SIZE = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        getExtras();
        init();
        setListeners();
        getListaContactos();
        ContactosController.getNrContactos(new WeakReference<>(textNrContactos), new WeakReference<>(getBaseContext()));
    }

    private void getExtras() {
        try {
            this.typeAction = getIntent().getExtras().getString(ContactosActivity.TYPE_ACTION, null);
        } catch (NullPointerException e) {
            e.getCause();
        }
    }

    private void setListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewContactos.addOnScrollListener(onScrollListener);
    }

    private void init() {
        this.recyclerViewContactos = findViewById(R.id.recyclerViewContactos);
        this.textNrContactos = findViewById(R.id.txtNrContactos);
        this.btnBack = findViewById(R.id.btnBack);
        this.contactosAdapter = new ContactosAdapter(this, typeAction);
        this.recyclerViewContactos.setAdapter(contactosAdapter);
    }

    private void getListaContactos() {
        isLoading = true;
        Query query = FirebaseFirestore.getInstance().collection(FirebaseConstants.USERS)
                .orderBy("timestampRegistro", Query.Direction.ASCENDING).limit(PAGE_SIZE);

        if (lastDocumentVisible == null) {
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    try {
                        isLoading = false;
                        if (task.isSuccessful() && task.getResult() != null) {
                            getContactos(task.getResult().getDocuments());

                        } else {
                            Toast.makeText(ContactosActivity.this, "Error!! no se pudo conseguir los contactos", Toast.LENGTH_SHORT).show();
                        }


                    } catch (NullPointerException e) {
                        e.getCause();
                    }

                }
            });

        } else {
            query.startAfter(lastDocumentVisible).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    try {
                        isLoading = false;
                        if (task.isSuccessful() && task.getResult() != null) {
                            getContactos(task.getResult().getDocuments());
                        } else {
                            Toast.makeText(ContactosActivity.this, "Error!! no se pudo conseguir los contactos", Toast.LENGTH_SHORT).show();
                        }


                    } catch (NullPointerException e) {
                        e.getCause();
                    }

                }
            });

        }

    }

    private void getContactos(List<DocumentSnapshot> documents) {
        if (documents.size() > 0) {
            lastDocumentVisible = documents.get(documents.size() - 1);
            for (DocumentSnapshot documentSnapshot : documents) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);

                if (usuario != null && !usuario.getUid().equals(FbUser.getCurrentUserId())) {
                    contactosAdapter.addContacto(usuario);

                }


            }
        } else {
            isLastPage = true;
        }
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            if (layoutManager != null) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                            firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE - 1) {
                        getListaContactos();
                    }
                }
            }

        }
    };

}