package com.example.threadhandler;

import android.os.AsyncTask;
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

    Button btnStart, btnEnd;
    ProgressBar progressBar;
    ProgressbarTask progressbarTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout_asynctasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = view.findViewById(R.id.btnStart);
        btnEnd = view.findViewById(R.id.btEnd);
        progressBar = view.findViewById(R.id.pb_start);

        btnStart.setOnClickListener(v -> {
            init();
        });
        btnEnd.setOnClickListener(v -> {
            progressbarTask.cancel(true);
        });
    }

    private void init() {
        progressBar.setMax(100);
        //Inicializamos el objeto de la clase
        progressbarTask = new ProgressbarTask();
        progressbarTask.execute();

    }

    private void longTask() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Defino la clase ProgressbarTask que hereda de AsynTask
    private class ProgressbarTask extends AsyncTask<Void, Integer, Boolean> {

        //Metodo de inicializacion
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //1. Inicializacion del progressbar
            progressBar.setProgress(0);
            btnStart.setEnabled(false);
            btnEnd.setEnabled(true);
        }


        //Metodo de finalizacion que se ejecuta en el hilo principal si no se ha cancelado el hilo
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            btnStart.setEnabled(true);
            btnEnd.setEnabled(false);
            if (aBoolean == true)
                Toast.makeText(getActivity(), "Tarea finalizada", Toast.LENGTH_SHORT).show();
        }

        //Metodo que se ejecuta en el hilo secundario y quew contiene la tarea larga
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 0; i < 100; i++) {
                if (!isCancelled()) {
                    longTask();
                    onProgressUpdate(1);
                } else {
                    return false;
                }
            }

            return true;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getActivity(), "Tarea cancelada", Toast.LENGTH_SHORT).show();
            btnEnd.setEnabled(false);
            btnStart.setEnabled(true);
        }

        //Metodo de actualizacion que se ejecuta en el hilo principal
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //2. Actualizacion en los intervalos de la tarea larga
            progressBar.incrementProgressBy(values[0].intValue());
        }
    }
}