package com.example.threadhandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HandlerFragment extends Fragment {

    Button btnStart;
    ProgressBar progressBar;
    Handler handler;
    static final int INIT_PROGRESS=0;
    static final int INCREMENT_PROGRESS=1;
    static String MESSAGE="message";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout_progressbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart=view.findViewById(R.id.btnStart);
        progressBar =view.findViewById(R.id.pb_start);

        btnStart.setOnClickListener(v -> init());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se utiliza el objeto Looper del hilo principal
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case INIT_PROGRESS:
                        btnStart.setEnabled(false);
                        progressBar.setProgress(0);
                        break;
                    case INCREMENT_PROGRESS:
                        progressBar.incrementProgressBy(1);
                        break;

                }

                String message = msg.getData().getString(MESSAGE);
                //Tengo que comprobar que no sea null
                if(message != null) {
                    btnStart.setEnabled(true);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }

            }

        };
    }



    private void init() {
        //1. Inicializacion del progressbar
        progressBar.setMax(100);

        new Thread(() -> {
            //1. Mandamos un mensaje o bien un objeto Runnable
            handler.sendEmptyMessage(INIT_PROGRESS);

            for (int i = 0; i < 100; i++) {
                longTask();

                //2. Actualizacion en los intervalos de la tarea larga
                handler.sendEmptyMessage(INCREMENT_PROGRESS);
            }

            //3.Informar que la tarea ha terminado
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE,"Operacion finalizada correctamente");
            message.setData(bundle);
            handler.sendMessage(message);

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
