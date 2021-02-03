package com.example.threadhandler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class AsyntacksFragment extends Fragment {

    Button btnStart;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout_progressbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = view.findViewById(R.id.btnStart);
        progressBar = view.findViewById(R.id.pb_start);

        btnStart.setOnClickListener(v -> {
            init();
        });
    }

    private void init() {
        //1. Inicializacion del progressbar
        progressBar.setMax(100);
        progressBar.setProgress(0);

        for (int i = 0; i < 100; i++) {
            longTask();

            //2. Actualizacion en los intervalos de la tarea larga
            progressBar.incrementProgressBy(1);
        }

        //3.Informar que la tarea ha terminado
        Toast.makeText(getActivity(), "Tarea finalizada", Toast.LENGTH_SHORT).show();

    }

    private void longTask() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}