package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton audio;
    private MediaPlayer mp;
    private int posicion;// variable para almacenar la posicion donde la musica se ha pausado
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Definicion de botones
        audio= (ImageButton) findViewById(R.id.BotonAudio);
        mp = MediaPlayer.create(this,R.raw.tetris99maintheme);
        mp.start();//Comienza la reproduccion de la musica de fondo
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                    posicion=mp.getCurrentPosition();
                    audio.setImageResource(R.drawable.jugar);
                }
                else{
                    mp.seekTo(posicion);
                    mp.start();
                    audio.setImageResource(R.drawable.pausa);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mp.isPlaying()){
            mp.pause();
            posicion=mp.getCurrentPosition();;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mp.isPlaying()){
            mp.seekTo(posicion);
            mp.start();
        }
    }

    public void iniciarModoNormal(View v){
        //Metodo para iniciar el activity de Modo normal
        Intent modoNormal = new Intent(this, Tablero.class);
        startActivity(modoNormal);
    }
}
