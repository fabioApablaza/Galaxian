package com.example.galaxian;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Timer;

public class Juego extends AppCompatActivity {
    //El bojecto que manejera la vista (view)
    private TDView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        // Carga la resolucion dentro de un objeto Point
        Point size = new Point();
        display.getSize(size);
        gameView = new TDView(this,size.x,size.y);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
