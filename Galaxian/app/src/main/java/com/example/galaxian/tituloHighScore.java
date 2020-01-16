package com.example.galaxian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class tituloHighScore {
    private Bitmap bitmap;
    //posicion de en el canvas
    private float x, y;
    //Constructor
    public tituloHighScore(Context context, int pantX, int pantY){
        x=pantX/3;
        y=-120;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.highscore1);
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
