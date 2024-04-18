package com.mirea.lutchenkoam.data_thread;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final Handler handler = new Handler();

        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
//                binding.tvInfo.setText("runnable1");
            }
        };

        final Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
//                binding.tvInfo.setText("runnable2");
            }
        };

        final Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
//                binding.tvInfo.setText("runnable3");
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    handler.post(runnable1);
                    TimeUnit.SECONDS.sleep(1);
                    handler.post(runnable2);
                    TimeUnit.SECONDS.sleep(2);
                    handler.post(runnable3);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}