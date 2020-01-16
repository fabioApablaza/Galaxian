package com.example.tetris;

public class Tetrominó {
    public int x1, y1;
    public int x2, y2;
    public int x3, y3;
    public int x4, y4;
    public Tetrominó(int tipo){
        switch (tipo){
            case 1://tetromino cuadrado
                x1 = 0; y1 = 7;
                x2 = 0; y2 = 8;
                x3 = 1; y3 = 7;
                x4 = 1; y4 = 8;
                break;

            case 2:    //tetromino Z
                x1 = 0;y1 = 7;
                x2 = 0;y2 = 8;
                x3 = 1;y3 = 8;
                x4 = 1;y4 = 9;

                break;

            case 3: //tetromino I
                x1 = 0;y1 = 6;
                x2 = 0;y2 = 7;
                x3 = 0;y3 = 8;
                x4 = 0;y4 = 9;
                break;

            case 4: //tetromino T
                x1 = 0;y1 = 8;
                x2 = 1;y2 = 7;
                x3 = 1;y3 = 8;
                x4 = 1;y4 = 9;

                break;

            case 5: //tetromino S
                x1 = 0;y1 = 7;
                x2 = 0;y2 = 8;
                x3 = 1;y3 = 6;
                x4 = 1;y4 = 7;
                break;

            case 6:  //tetromino J
                x1 = 0;y1 = 7;
                x2 = 0;y2 = 8;
                x3 = 0;y3 = 9;
                x4 = 1;y4 = 9;
                break;

            case 7:  //tetromino L
                x1 = 0;y1 = 7;
                x2 = 0;y2 = 8;
                x3 = 0;y3 = 9;
                x4 = 1;y4 = 7;
                break;
        }
        }
    public void moverse(int x, int y) {
        x1 = x1 + x;
        y1 = y1 + y;
        x2 = x2 + x;
        y2 = y2 + y;
        x3 = x3 + x;
        y3 = y3 + y;
        x4 = x4 + x;
        y4 = y4 + y;
    }

}
