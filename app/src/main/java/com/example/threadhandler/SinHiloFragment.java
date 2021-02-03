package com.example.threadhandler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SinHiloFragment extends Fragment {


    Button btnStart;
    ProgressBar progressBar;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout_progressbar, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = view.findViewById(R.id.btnStart);
        progressBar = view.findViewById(R.id.pb_start);

        btnStart.setOnClickListener(v -> init());
    }

    /**
     * Este metodo incrementa en 10 unidades la barra de proceso mientras realiza una tarea larga que dura 1 segundo
     */
    private void init() {
        //1. Inicializacion del progressbar
        progressBar.setMax(100);
        progressBar.setProgress(0);

        for (int i = 0; i < 10; i++) {
            longTask();

            //2. Actualizacion en los intervalos de la tarea larga
            progressBar.incrementProgressBy(10);
        }

        //3.Informar que la tarea ha terminado
        Toast.makeText(getActivity(), "Tarea finalizada", Toast.LENGTH_SHORT).show();

    }

    private void longTask() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}