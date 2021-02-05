package com.hammer67.watsappclone.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.models.Usuario;
import com.hammer67.watsappclone.activities.viewholder.ContactoEstadoViewHolder;
import com.hammer67.watsappclone.activities.vistas.ContactosActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactosAdapter extends RecyclerView.Adapter<ContactoEstadoViewHolder> {

    private Activity activity;
    private List<Usuario> list;
    private String typeAction;

    public ContactosAdapter(Activity activity, String typeAction) {
        this.activity = activity;
        this.list = new ArrayList<>();
        this.typeAction = typeAction;
    }

    @NonNull
    @Override
    public ContactoEstadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contacto_estado, parent, false);
        return new ContactoEstadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoEstadoViewHolder holder, int position) {
        Usuario usuario = list.get(holder.getAdapterPosition());

        holder.textNombre.setText(usuario.getNombre());
        String estado = usuario.getEstado();

        if (estado != null && estado.length() > 0) {
            holder.textEstado.setText(estado);
            holder.textEstado.setVisibility(View.VISIBLE);
        } else {
            holder.textEstado.setVisibility(View.GONE);
        }

        if (!activity.isFinishing()) {
            Glide.with(activity).load(usuario.getImageUrl()).error(R.drawable.ic_baseline_person_grey).into(holder.img);
        }

        switch (typeAction) {
            case ContactosActivity.ACTION_CHAT:

                break;

            case ContactosActivity.ACTION_CALL:
                holder.btnLlamada.setVisibility(View.VISIBLE);
                holder.btnVideoLlamada.setVisibility(View.VISIBLE);

                holder.btnLlamada.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "Llamando a: " + usuario.getNombre(), Toast.LENGTH_SHORT).show();
                    }
                });

                holder.btnVideoLlamada.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(activity, "Video llamada a: " + usuario.getNombre(), Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case ContactosActivity.ACTION_SEND_MESSAGE:

                break;


        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addContacto(Usuario usuario) {
        list.add(usuario);
        notifyDataSetChanged();
    }
}
