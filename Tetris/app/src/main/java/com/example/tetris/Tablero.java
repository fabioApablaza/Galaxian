package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Tablero extends AppCompatActivity {
    private Bitmap[] piezas= new Bitmap[7];
    private int xPos, yPos, yDireccion,xDireccion;
    private ImageButton botonDerecho, botonIzquierdo, botonAbajo, botonGiro;
    private Button botonRestar, botonJugar;
    private JuegoTetris unJuegoTetris;
    private FrameLayout tablerito,proximoTetrominio;
    private Timer unTimer;
    private long TIMER_INTERVAL=30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);

        //posicion de los Bitmaps (osea los tetrominios)
        xPos= 200;
        yPos=0;
        yDireccion=1;
        xDireccion=0;

        piezas[0]= BitmapFactory.decodeResource(getResources(),R.drawable.spiece);
        piezas[1]= BitmapFactory.decodeResource(getResources(),R.drawable.ipiece);
        piezas[2]= BitmapFactory.decodeResource(getResources(),R.drawable.lpiece);
        piezas[3]= BitmapFactory.decodeResource(getResources(),R.drawable.tpiece);
        piezas[4]= BitmapFactory.decodeResource(getResources(),R.drawable.zpiece);
        piezas[5]= BitmapFactory.decodeResource(getResources(),R.drawable.square);
        piezas[6]= BitmapFactory.decodeResource(getResources(),R.drawable.jpiece);

        //Definiendo los botones
        botonDerecho= (ImageButton)findViewById(R.id.botonDerecha);
        botonIzquierdo= (ImageButton) findViewById(R.id.botonIzquierda);
        botonAbajo= (ImageButton) findViewById(R.id.botonAbajo);
        botonGiro= (ImageButton) findViewById(R.id.botonGiro);
        botonJugar= (Button) findViewById(R.id.botonJugar);
        botonRestar=(Button) findViewById(R.id.botonReset);

        botonDerecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unJuegoTetris.moverADerecha();
            }
        });
        botonIzquierdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unJuegoTetris.moverAIzquierda();
            }
        });

        //Creacion del framelayout que contendrá lo que se mostrara del juego
        proximoTetrominio= (FrameLayout) findViewById(R.id.proximoTetrominio);
        proximoTetrominio.setBackgroundColor(Color.GRAY);

        //Creacion del tablero donde se visualiza el juego y definicion de sus parametros
        unJuegoTetris = new JuegoTetris(this);
        tablerito = (FrameLayout) findViewById(R.id.tablerito);
        unJuegoTetris.setBackgroundColor(Color.WHITE);
        tablerito.addView(unJuegoTetris);

        unTimer= new Timer();
        unTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                unJuegoTetris.invalidate();
            }
        },0,TIMER_INTERVAL);
    }






}
