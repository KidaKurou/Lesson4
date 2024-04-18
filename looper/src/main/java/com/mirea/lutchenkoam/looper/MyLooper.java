package com.mirea.lutchenkoam.looper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;

public class MyLooper extends Thread {
    private int age;
    private String occupation;
    public MyLooper(int age, String occupation) {
        this.age = age;
        this.occupation = occupation;
    }

    @Override
    public void run() {
        Log.d("MyLooper", "run");

        // Подсчет времени задержки (в миллисекундах) в зависимости от возраста
        long delayTime = age * 1000;

        // Создаем новый Handler для основного потока
        Handler mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String result = bundle.getString("result");
                Log.d("MyLooper", result);
            }
        };

        // Отправляем сообщение с результатом в основной поток через Handler
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("result", "Your age: " + age + ", Your occupation: " + occupation);
        message.setData(bundle);
        mainHandler.sendMessage(message);

        // Задержка выполнения потока
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Отправляем сообщение с результатом в основной поток через Handler после задержки
        message = new Message();
        bundle = new Bundle();
        bundle.putString("result", "Time delay complete. Your age: " + age + ", Your occupation: " + occupation);
        message.setData(bundle);
        mainHandler.sendMessage(message);
    }
}

//public class MyLooper extends Thread{
//    public Handler mHandler;
//    private final Handler mainHandler;
//    public MyLooper(Handler mainThreadHandler) {
//        mainHandler = mainThreadHandler;
//    }
//
//    public void run() {
//        Log.d("MyLooper", "run");
//        Looper.prepare();
//        mHandler = new Handler(Looper.myLooper()) {
//            public void handleMessage(Message msg) {
//                String data = msg.getData().getString("KEY");
//                Log.d("MyLooper get message: ",  data);
//
//                int count = data.length();
//                Message message = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("result", String.format("The number of letters in the word %s is %d ",data,count));
//                message.setData(bundle);
//                // Send the message back to main thread message queue use main thread message Handler.
//                mainHandler.sendMessage(message);
//            }
//        };
//        Looper.loop();
//    }
//}