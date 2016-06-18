package com.example.vladyslav_kovega.myapplication;

import android.graphics.Point;

/**
 * Класс для хранения текущей позиции пальца и предыдущей
 */
public class Finger {

    public int id; // id пальца
    public Point now;
    public Point before;
    private boolean enable = false; // было ли сделано движение

    public Finger (int id, int x, int y){
        this.id = id;
        now = before = new Point(x, y); // конструктор устанавливающий идентификатор пальца и координаты
    }

    /*
     * Метод setNow проверяет был ли палец перемещен, если да то устанавливает разные координаты текущую
     * и предыдущую.
     */
    public void setNow (int x, int y){
        if (!enable){
            enable = true;
            now = before = new Point(x, y);
        }else {
            before = now;
            now = new Point(x,y);
        }
    }
}

