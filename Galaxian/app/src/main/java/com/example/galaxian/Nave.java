package com.example.galaxian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Nave {
    RectF rect;
    //La nave del jugador es representada por un bitmap
    private Bitmap bitmap;
    //El ancho y la altura de la nave
    private float anchura, altura; //posicion de la nave en la pantalla
    //El extremo izquierdo de la forma de la nave
    private float x;
    //La cordeenada superior
    private float y;
    //velocidad de la nave
    private float velocidad = 0;
    // Las maneras en la que se puede mover la nave
    public final int PARAR = 0;
    public final int IZQUIERDA = 1;
    public final int DERECHA = 2;
    //
    private int movimientoNave = PARAR;

    private int extremoDerX;

    // Constructor
    public Nave(Context context,int pantX, int pantY) {
        // Inicializa un reft en blanco
        rect = new RectF();

        anchura = pantX/10;
        altura = pantY/10;

        x =pantX/2;
        y = pantY-400;

        extremoDerX=pantX-120;
        // Inicializa el bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jugador);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (anchura),
                (int) (altura),
                false);

        // La velocidad de la nave en pixeles por segundo
        velocidad = 350;
    }
    public void actulizar(long fps) {
        if(movimientoNave == IZQUIERDA&& x>0){
            x = x - velocidad / fps;
        }

        if(movimientoNave == DERECHA && x<extremoDerX){
            x = x + velocidad / fps;
        }

        // actualiza rect para detectar colociones
        rect.top = y;
        rect.bottom = y + altura;
        rect.left = x;
        rect.right = x + anchura;

    }

    //Getters y setters
    public Bitmap getBitmap() {
        return bitmap;
    }
    public float getVelocidad() {
        return velocidad;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public float getLongitud() {
        return anchura;
    }
    public void setMovementState(int i){
        movimientoNave = i;
    }
    public RectF getRect(){
        return rect;
    }
}
