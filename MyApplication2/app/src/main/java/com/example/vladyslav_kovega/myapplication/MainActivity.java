package com.example.vladyslav_kovega.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Устанавливаем в качестве основного макета класс визуализации наследованый от View.
//
        setContentView(new ApplicationView(this));
    }
}
