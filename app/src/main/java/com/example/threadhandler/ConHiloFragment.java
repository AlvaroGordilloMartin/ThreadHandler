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

public class ConHiloFragment extends Fragment {


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

    private void init() {
        progressBar.setMax(100);

        //Se crea el hilo o subprocreso en el cual no puedo acceder directamente a los widgtes del UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                //1. Inicializacion del progressbar
                progressBar.post(() -> {
                    progressBar.setProgress(0);
                });

                //Bloqueo el boton para lanzar varios hilos
                btnStart.post(() -> {
                    btnStart.setEnabled(false);
                });

                //Tarea que bloqueaba la interfaz
                for (int i = 0; i < 100; i++) {
                    longTask();

                    progressBar.post(() -> {
                        //2. Actualizacion en los intervalos de la tarea larga
                        progressBar.incrementProgressBy(1);
                    });

                }//for

                //Aqui no se actualizan componentes por tanto no puedo utilizar el metodo post que ya tiene implementado
                //Se utiliza el metodo runOiThread de Activity
                getActivity().runOnUiThread(() -> {
                    //3.Informar que la tarea ha terminado
                    Toast.makeText(getActivity(), "Tarea finalizada", Toast.LENGTH_SHORT).show();
                });
                //Modificar el estado del boton
                btnStart.post(() -> {
                    btnStart.setEnabled(true);
                });
            }
        }).start();


    }

    private void longTask() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}