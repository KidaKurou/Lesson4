package com.mirea.lutchenkoam.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.lutchenkoam.thread.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Get information about main thread and changing his name
        Thread mainThread = Thread.currentThread();
        binding.resultTextView.setText("Current thread's name: " + mainThread.getName());

        //Change name and output in text view
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-09-21, НОМЕР ПО СПИСКУ: 17, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Зеленая миля");
        binding.resultTextView.append("\n Новое имя потока: " + mainThread.getName());

        //Output Log
        Log.d(MainActivity.class.getSimpleName(), "Stack^ " + Arrays.toString(mainThread.getStackTrace()));

        //Output information about group of thread in Log
        Log.d(MainActivity.class.getSimpleName(), "Group: " + mainThread.getThreadGroup());

        binding.calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAveragePairs();
            }
        });

        binding.mireaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int numberThread = counter++;
                        Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы № %s номер по списку %d",
                                numberThread, "БСБО-09-21", 17));
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject", "Выполнен поток № " + numberThread);
                        }
                    }
                }).start();
            }
        });
    }

    private void calculateAveragePairs(){
        //Подсчет среднего количества пар в месяц
        new Thread(new Runnable() {
            @Override
            public void run() {
                final double averagePairs = calculateAveragePairsInBackground();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.resultTextView.setText("Среднее количество пар в день: " + averagePairs);
                    }
                });
            }
        }).start();
    }

    private double calculateAveragePairsInBackground(){
        //Вычесление среднего количество пар в месяц в фоновом потоке
        return 4.5;
    }

}