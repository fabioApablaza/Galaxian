package com.example.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.EventListener;
import java.util.Random;
import java.util.Timer;

public class JuegoTetris extends View {
    private Bitmap[] piezas= new Bitmap[7];
    private int alturaMaximaPieza1,alturaMaximaPieza2,alturaMaximaPieza3;
    private int posicionX, posicionY, descenso;
    public JuegoTetris(Context context) {
        super(context);

        //definicion de las alturas maximas de cada pieza
        alturaMaximaPieza1=800;//spiece,zpiece,tpiece,square
        alturaMaximaPieza2=750;//lpiece,jpiece
        alturaMaximaPieza3=850;//ipiece
        //definicion de las anchuras maximas de cada pieza


        //definicion de la posicion de la pieza
        posicionX=200;
        posicionY=0;
        descenso=2;

        piezas[0]= BitmapFactory.decodeResource(getResources(),R.drawable.spiece);
        piezas[1]= BitmapFactory.decodeResource(getResources(),R.drawable.ipiece);
        piezas[2]= BitmapFactory.decodeResource(getResources(),R.drawable.lpiece);
        piezas[3]= BitmapFactory.decodeResource(getResources(),R.drawable.tpiece);
        piezas[4]= BitmapFactory.decodeResource(getResources(),R.drawable.zpiece);
        piezas[5]= BitmapFactory.decodeResource(getResources(),R.drawable.square);
        piezas[6]= BitmapFactory.decodeResource(getResources(),R.drawable.jpiece);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();

        canvas.drawBitmap(piezas[1],posicionX,posicionY,p);
        posicionY+=descenso;
        if(posicionY>alturaMaximaPieza3){
            posicionY=alturaMaximaPieza3;
            canvas.drawBitmap(piezas[1],posicionX,posicionY,p);
        }

        //Toast.makeText(getContext(),"with: "+canvas.getWidth()+" yDireccion: "+canvas.getHeight(),Toast.LENGTH_SHORT).show();

    }
    public void moverADerecha(){
        posicionX+=20;
    }
    public  void moverAIzquierda(){
        posicionX-=20;
    }
}
