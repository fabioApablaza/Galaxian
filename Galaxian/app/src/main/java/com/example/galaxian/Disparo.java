package com.example.galaxian;

import android.graphics.RectF;

public class Disparo {
    private float x;
    private float y;

    private RectF rect;

    public final int ARRIBA = 0;
    public final int ABAJO = 1;
    public final int QUIETO = -1;

    //
    int heading = -1;
    float velocidad =  850;

    private int anchura = 1;
    private int altura;

    private boolean activo;

    public Disparo(int pantallaY) {

        altura = pantallaY / 20;
        activo = false;

        rect = new RectF();
    }
    public RectF getRect(){
        return  rect;
    }

    public boolean getEstado(){
        return activo;
    }

    public void reiniciar(){
        activo = false;
    }

    public float getPuntoDeImpactoY(){
        if (heading == ABAJO){
            return y + altura;
        }else{
            return  y;
        }

    }
    public boolean disparo(float inicioX, float inicioY, int direccion) {
        boolean res=false;
        if (!activo) {
            x = inicioX;
            y = inicioY;
            heading = direccion;
            activo = true;
            res= true;
        }

        // Las balas ya estan activadas y retorna false
        return res;
    }
    public void actualizacion(long fps){

        if(heading == ARRIBA ){
            y = y - velocidad / fps;
        }else{
            if(heading==ABAJO){
                y = y + velocidad / fps;
            }
        }

        // Actualiza Rect
        rect.left = x;
        rect.right = x + anchura;
        rect.top = y;
        rect.bottom = y + altura;

    }
}
