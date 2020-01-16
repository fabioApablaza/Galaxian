package com.example.galaxian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.Random;

public class Invasor {
    RectF rect;

    Random generator = new Random();

    // La nave del jugador que es representado por dos bitmaps
    private Bitmap bmap;

    // la longitud y la anchura del invasor
    private float longitud;
    private float altura;

    //El extremo izquierdo del invasor
    private float x;

    // La coordenada superior
    private float y;

    // La velocidad en pixeles por segundo que se mueve el invasor
    private float velocidad;

    public final int IZQUIERDA = 1;
    public final int DERECHA = 2;

    // En que direccion se mueve el invasor
    private int movimiento= DERECHA;

    boolean esVisible;

    private int pantY;
    private int pantX;
    private float posEjercito;

    private boolean modoKamizaze;
    private boolean vuelta;
    //Constructor
    public Invasor(Context context, int fila, int columna, int pantallaX, int pantallaY,int idBitmap) {
        // Inicializa un rectf en blanco
        rect = new RectF();

        longitud = pantallaX / 20;
        altura = pantallaY / 20;

        esVisible = true;

        x = 100 + (columna * 150);
        y = 300 + (fila * 150);

        modoKamizaze=false;
        vuelta=false;

        posEjercito=y;
        pantX=pantallaX;
        pantY=pantallaY;
        iniciarBitmap(context,idBitmap);

        velocidad = 2;
    }
    private void iniciarBitmap(Context context,int idBitmap){
        // Inicializa los bitmaps
        switch (idBitmap){
            case 1:{
                bmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.invasor);
                break;
            }
            case 2:{
                bmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.invasor3);
                break;
            }
            case 3:{
                bmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo);
                break;
            }
            case 4:{
                bmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.enemigo1);
                break;
            }
        }
        // modifica el bitmap para tenga un tamaño adeacuado
        bmap= Bitmap.createScaledBitmap(bmap,
                (int) (longitud),
                (int) (altura),
                false);
    }

    public void actualizar(){

            if(movimiento == IZQUIERDA ){
                x = x - velocidad ;
            }

            if(movimiento == DERECHA){
                x = x + velocidad ;
            }

            // Actualiza el rect
            rect.top = y;
            rect.bottom = y + altura;
            rect.left = x;
            rect.right = x + longitud;



    }
    public void cambioDeLado(){
        if(movimiento == IZQUIERDA){
            movimiento = DERECHA;
        }else{
            movimiento = IZQUIERDA;
        }

    }
    public boolean simulacionDisparo(float jugadorX, float longitudJugador){
        /*Metodo utlizado para detectar si el invasor esta alineado horizontalmente con la nave del jugador,
        calculara un numero aleatorio de 1 en 150 para determinar si disparara*/
        int random = -1;
        boolean res=false;
        // si esta cerca de la nave del jugador
        if((jugadorX + longitudJugador > x &&
                jugadorX + longitudJugador < x + longitud) || (jugadorX > x && jugadorX < x + longitud)) {

            //si random es 0 dispara
            random = generator.nextInt(150);
            if(random== 0) {
                res= true;
            }

        }

        // Si no esta cerca del jugador tiene una chance de 1 en 2000 de disparar
        random = generator.nextInt(2000);
        if(random == 0){
            res= true;
        }

        return res;
    }
    public void ataqueKamiKaze(){
        //Simulacion del ataque Kamikaze que tienen los invasores
        if(modoKamizaze){
            if (y<pantY){
                y+=6;
                if(vuelta&& y==posEjercito){
                    modoKamizaze=false;
                }
            }else {
                y=0;
                vuelta=true;
            }
            // Actualiza el rect
            rect.top = y;
            rect.bottom = y + altura;
            rect.left = x;
            rect.right = x + longitud;

        }

    }
    //getters y setters
    public void setInvisible(){
        esVisible = false;
    }

    public boolean getVisibilidad(){
        return esVisible;
    }

    public RectF getRect(){
        return rect;
    }

    public Bitmap getBitmap(){
        return bmap;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getLongitud(){
        return longitud;
    }
    public int getMovimiento(){
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }
    public void setModoKamikaze(){
        if(modoKamizaze){
            modoKamizaze=false;
        }else{
            modoKamizaze=true;
        }
    }
    public boolean getModoKamikaze(){
        return modoKamizaze;
    }

}
