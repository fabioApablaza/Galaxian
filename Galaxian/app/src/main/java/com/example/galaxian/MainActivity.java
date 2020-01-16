package com.example.galaxian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button botonIniciar;
    int posicion;
    MediaPlayer musica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Definicion de la musica de fondo
        musica = MediaPlayer.create(this,R.raw.galagatheme);
        musica.start();

        //definicion del boton iniciar
        botonIniciar= findViewById(R.id.botonIniciar);

    }
    @Override
    protected void onPause() {
        super.onPause();
        if(musica.isPlaying()){
            musica.pause();
            posicion=musica.getCurrentPosition();;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!musica.isPlaying()){
            musica.seekTo(posicion);
            musica.start();
        }
    }

    @Override
    public void onClick(View v) {
        //Metodo para iniciar el activity de Modo normal
        Intent juego = new Intent(this, Juego.class);
        startActivity(juego);
    }
}
