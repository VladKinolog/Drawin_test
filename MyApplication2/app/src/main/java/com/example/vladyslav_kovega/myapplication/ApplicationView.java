package com.example.vladyslav_kovega.myapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 *
 */
public class ApplicationView extends View {

    private static final String LOG_TAG = "myLogs";


    Canvas canvas;
    Bitmap image;  // Переменные для создания холста и отрисовки в нем обьекта bitmap;
    Paint paint = new Paint();
    static float density; //    Переменная хранения плотности экрана
    float zoom = 500; // понадобится для зумирования холста
    Point position = new Point(50,50);

    ArrayList<Finger> fingers = new ArrayList<Finger>();



    // Нужно создать конструктор класса для получения котекста Вью.
    public ApplicationView(Context context){
        super(context);
//        Получение текущей плотности экрана
        density = getResources().getDisplayMetrics().density;
        this.setBackgroundColor(Color.GRAY); // Окрашивание в серый цвет Бекграунда экрана.
        paint.setStrokeWidth(density*5);  // Установка кисти в 5dp (изночально узнав плотность пикселей)
        image = Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_4444); // Создание наполнения холста
        canvas = new Canvas(image); // Холст на основе наполнения
        canvas.drawColor(Color.WHITE); // Окрашивание в белый цвет

        Log.v(LOG_TAG,"Плотность пикселей равна --->> "+ density);
    }

    public void onDraw (Canvas canvas){
        canvas.translate(position.x, position.y); //задаем смещение холста
        canvas.scale(zoom/500, zoom/500); // зумирование
        canvas.drawBitmap(image, 0, 0, paint); //отрисовка

//        canvas.restore();				// Сбрасываем показатели отдаления и перемещения
//        for(int i = 0; i < fingers.size(); i++){	// Отображаем все касания в виде кругов
//            canvas.drawCircle(fingers.get(i).now.x, fingers.get(i).now.y, 50 * density, paint);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int id = event.getPointerId(event.getActionIndex()); // получение идентификатор пальца и получение индекса действия.
        int action = event.getActionMasked(); // получение информации о движении.
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN){            // при косании и последующем косании добавляем
            fingers.add(event.getActionIndex(), new Finger(id, (int)event.getX(), (int)event.getY()));  // касание в массив.
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP){
            fingers.remove(event.getActionIndex()); // удаление касания из масива при отпускании
        } else if (action == MotionEvent.ACTION_MOVE){
            for (int i = 0; i<fingers.size(); i ++){
                fingers.get(i).setNow((int)event.getX(i),(int)event.getY(i));
            }
            checkGestures();
            invalidate();
        }

        return true;
    }

    public void checkGestures(){
        Finger point = fingers.get(0); // нужный палец для таскания
        if(fingers.size() > 1) {
             float nowN = checkDistance(point.now, fingers.get(1).now);
             float beforeB = checkDistance(point.before, fingers.get(1).before);
             float oldSize = zoom;

            zoom = Math.max(nowN - beforeB + zoom, density*25);
            position.x -= (zoom - oldSize)/2;
            position.y -= (zoom - oldSize)/2;
        }else {
             position.x += point.now.x - point.before.x;
             position.y += point.now.y - point.before.y;

        }
    }
    static float checkDistance (Point p1, Point p2){
        return (float) Math.sqrt((p1.x - p2.x)*(p1.x - p2.x)+(p1.y - p2.y)*(p1.y - p2.y));
    }
}

