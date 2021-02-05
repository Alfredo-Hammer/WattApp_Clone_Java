package com.hammer67.watsappclone.activities.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hammer67.watsappclone.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactoEstadoViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView img;
    public TextView textNombre, textEstado;
    public ImageButton btnLlamada, btnVideoLlamada;


    public ContactoEstadoViewHolder(@NonNull View itemView) {
        super(itemView);

        this.img = itemView.findViewById(R.id.imgContactos);
        this.textNombre = itemView.findViewById(R.id.txtNombreContacto);
        this.textEstado = itemView.findViewById(R.id.txtEstadoContacto);
        this.btnLlamada = itemView.findViewById(R.id.btnLlamadaContacto);
        this.btnVideoLlamada = itemView.findViewById(R.id.btnVideoContacto);


    }
}
