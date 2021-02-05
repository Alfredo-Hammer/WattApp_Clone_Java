package com.hammer67.watsappclone.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hammer67.watsappclone.R;
import com.hammer67.watsappclone.activities.utils.TxtUtils;

import java.util.ArrayList;

public class LlamadasFragment extends Fragment {

    private TextView txtWSLlamadas;

    public LlamadasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_llamadas, container, false);
        init(view);
        setIconWSTxtInfo();

        return view;
    }

    private void setIconWSTxtInfo() {
        ArrayList<String> listChars = new ArrayList<>();
        listChars.add("$");

        ArrayList<Integer> listImagenes = new ArrayList<>();
        listImagenes.add(R.drawable.ic_call_green);

        TxtUtils.setIconInTxtView(txtWSLlamadas, getString(R.string.txt_llamadas_info),listChars, listImagenes,requireContext());
    }

    private void init(View view) {
        this.txtWSLlamadas = view.findViewById(R.id.txtWSLlamadas);
    }
}