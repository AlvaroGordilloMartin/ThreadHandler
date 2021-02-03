package com.example.threadhandler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends Fragment {

    Button btnSinHilo;
    Button btnConHilo;
    Button btnHandler;
    Button btnAsyntacks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSinHilo = view.findViewById(R.id.btnSinHilo);
        btnConHilo = view.findViewById(R.id.btnConHilo);
        btnHandler = view.findViewById(R.id.btnHandler);
        btnAsyntacks = view.findViewById(R.id.btnAsyn);

        btnSinHilo.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_sinHiloFragment));

        btnConHilo.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_conHiloFragment));

        btnHandler.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_handlerFragment));

        btnAsyntacks.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_asyntacksFeragment));
    }
}