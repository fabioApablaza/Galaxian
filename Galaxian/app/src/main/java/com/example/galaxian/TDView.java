package com.example.galaxian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class TDView extends SurfaceView implements Runnable {
    Context context;

    tituloHighScore unTitulo;
    //Variable para indicar cuando se este jugando o no
    //Dependiendo de su estado se interrumpira su ejecución
    private volatile boolean jugando;

    private boolean pausado;
    //Hilo del juego
    private Thread gameThread= null;
    // El tamaño de la pantalla en pixeles
    private int pantallaX;
    private int pantallaY;
    //La nave que manejara el jugador
    private Nave jugador;
    //El disparo que saldra de la nave
    private Disparo unDisparo;
    // Los disparos de los invasores
    private Disparo[] disparosInvasores = new Disparo[200];
    private int proximoDisparo;
    private int maxDisparoAliens = 10;
    // Arreglos de los aliens
    private Invasor[] invasores = new Invasor[24];
    int numInvasores = 0;
    // Objetos para dibujar
    private Paint paint;
    private Canvas canvas;
    //El surfaceHolder se utiliza para bloquear la superficie antes de dibujar los graficos
    private SurfaceHolder unHolder;

    private long fps;

    private long timeThisFrame;
    // Efectos de sonido del juego
    private SoundPool soundPool;

    private int playerExplodeID = -1;
    private int inicioNuevoJuego=-1;
    private int explosionInvasor = -1;
    private int disparoNave = -1;
    private int caidaInvasor=-1;
    private int damageShelterID = -1;

    // El puntaje
    int puntaje = 0;

    // Vidas del jugador
    private int vidas = 3;

    private Bitmap vidasBitmap;

    private Random aleatorio;
    private int idInvasor;

    private Bitmap fondoJuego;

    //Constructor de la clase
    public TDView(Context context,int x, int y) {
        //Se le pide a la clase surface que configure este objeto
        super(context);
        //Se define esta variable global para que pueda ser usada en otras clases
        this.context=context;
        // Inicializa los objetos de dibujos
        unHolder = getHolder();
        paint = new Paint();

        aleatorio= new Random();

        pantallaX=x;
        pantallaY=y;

        //Se define el fondo del canvas y se ajusta el tamaño de la pantalla del celular
        fondoJuego= BitmapFactory.decodeResource(context.getResources(),R.drawable.fondojuego);
        fondoJuego= Bitmap.createScaledBitmap(fondoJuego,
                x,
                y,
                false);

        //Definicion de los efectos de sonido
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        inicioNuevoJuego= soundPool.load(getContext(),R.raw.inicionuevojuego,1);
        disparoNave= soundPool.load(getContext(),R.raw.disparo,1);
        explosionInvasor= soundPool.load(getContext(),R.raw.explosionaveenemiga,1);
        caidaInvasor= soundPool.load(getContext(),R.raw.caidainvasor,1);
        this.prepararNivel();
    }

    @Override
    public void run() {
        pausado=true;
        while (jugando){
            long startFrameTime = System.currentTimeMillis();
            if(!pausado){//La ejecucion del juego continuara si el juego no esta pausado
                actualizar();
            }
            dibujar();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }
    private void prepararNivel(){
        //Definicion de todos los objetos del juego
        //Define el titulo del puntaje
        unTitulo= new tituloHighScore(context,pantallaX,pantallaY);
        //Se define a la nave del jugador
        jugador= new Nave(context,pantallaX,pantallaY);

        crearInvasores();//Metodo para definir a los invasores

        //Prepara los disparos del jugador
        unDisparo = new Disparo(pantallaY);

        // Inicializa el arreglo de disparos de los invasores

        for(int i = 0; i < disparosInvasores.length; i++){
            disparosInvasores[i] = new Disparo(pantallaY);
        }

        //Inicializa el bitmap de las vidas del jugador
        vidasBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.jugador3);
        soundPool.play(inicioNuevoJuego,1,1,0,0,1);
    }

    private void dibujar() {
        int j,m=pantallaY-200;
        if (unHolder.getSurface().isValid()) { //Verifica si la clase SrfaceHolder is correcta
            //Primero se bloquea el area de memoria que estaremos dibujando
            canvas = unHolder.lockCanvas();

            //Dibuja el fondo del canvas
            canvas.drawBitmap(fondoJuego,0,0,null);

            // Elige el color del pincel para dibujar
            paint.setColor(Color.argb(255,  255, 255, 255));

            //Dibuja la imagen del titulo del puntaje
            canvas.drawBitmap(unTitulo.getBitmap(),unTitulo.getX(),unTitulo.getY(),paint);


            // Dibuja al jugador
            canvas.drawBitmap(jugador.getBitmap(), jugador.getX(), jugador.getY(), paint);

            // Dibuja a los invasores
            for(int i = 0; i < numInvasores; i++){
                if(invasores[i].getVisibilidad()) {
                    canvas.drawBitmap(invasores[i].getBitmap(), invasores[i].getX(), invasores[i].getY(), paint);
                }
            }

            // Dibuja los disparos del jugador si estan activos
            if(unDisparo.getEstado()){
                canvas.drawRect(unDisparo.getRect(),paint);
            }

            // Dibuja los disparos de los invasores
            for(int i = 0; i < disparosInvasores.length; i++){
                if(disparosInvasores[i].getEstado()) {
                    canvas.drawRect(disparosInvasores[i].getRect(), paint);
                }
            }
            //Dibuja las vidas restantes del jugador
            for(int i=0; i<3; i++){
                j= 50+(i*80);
                if(i<vidas){
                    canvas.drawBitmap(vidasBitmap,j,m,paint);
                }
            }
            // Dibuja el puntaje del jugador
            paint.setColor(Color.argb(255,  255, 0, 0));
            paint.setTextSize(100);
            canvas.drawText(Integer.toString(puntaje), (pantallaX/3)+150,250, paint);
            if(pausado){//Si el juego esta pausado se muestra un texto por pantalla
                canvas.drawText("LISTO", (pantallaX/3)+150,pantallaY-500, paint);

            }

            paint.setColor(Color.argb(255,  249, 129, 0));

            // Desbloquea y dibuja el escenario
            unHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void actualizar() {
        // Variable para verificar si un alien choco contra un costado de la pantalla
        boolean choque = false;

        // Mueve la nave del jugador
        jugador.actulizar(fps);

        // Actualiza a los invasores si son visibles
            for(int i = 0; i < numInvasores; i++){

                if(invasores[i].getVisibilidad()) {
                    // Mueve el proximo invasor
                    invasores[i].actualizar();
                    if(invasores[i].getModoKamikaze()){//Si el invasor esta haciendo un atque kamikaze tambien actualiza su movimiento
                        invasores[i].ataqueKamiKaze();
                    }

                    // Dermina si el invasor quiere dispara o no
                    if(invasores[i].simulacionDisparo(jugador.getX(),
                            jugador.getLongitud())){

                        // Define el disparo del invasor
                        if(disparosInvasores[proximoDisparo].disparo(invasores[i].getX()
                                        + invasores[i].getLongitud() / 2,
                                invasores[i].getY(), unDisparo.ABAJO)) {

                            //Dispara y prepara el proximo disparo
                            proximoDisparo++;


                            if (proximoDisparo == maxDisparoAliens) {

                                proximoDisparo = 0;
                            }
                        }
                    }

                    if (invasores[i].getX() > (pantallaX-120)
                            || invasores[i].getX() < 0 ){

                        choque = true;

                    }
                }

            }



        // Actualiza todos los disparos de los invasores si estan activos
        for(int i = 0; i < disparosInvasores.length; i++){
            if(disparosInvasores[i].getEstado()) {
                disparosInvasores[i].actualizacion(fps);
            }
        }

        // si un invasor choco con alguno de los bordes de la pantalla, entonces todos los invasores se mueven hacia el lado contrario
        if(choque){
            for(int i = 0; i < numInvasores; i++){
                invasores[i].cambioDeLado();
            }
        }
        // Actuliza los disparos del jugador
        if(unDisparo.getEstado()){
            unDisparo.actualizacion(fps);
        }

        // verifica si los disparos del jugador tocaron el borde superior de la pantalla
        if(unDisparo.getPuntoDeImpactoY() < 0){
            unDisparo.reiniciar();
        }
        // Verifica si los disparos de los invasores tocaron el fondo de la pantalla
        for(int i = 0; i < disparosInvasores.length; i++){
            if(disparosInvasores[i].getPuntoDeImpactoY() > pantallaY){
                disparosInvasores[i].reiniciar();
            }
        }
        // Verifica si los disparos del jugador colisionan con los invasores
        if(unDisparo.getEstado()) {
            for (int i = 0; i < numInvasores; i++) {
                if (invasores[i].getVisibilidad()) {
                    if (RectF.intersects(unDisparo.getRect(), invasores[i].getRect())) {
                        invasores[i].setInvisible();
                        soundPool.play(explosionInvasor, 1, 1, 0, 0, 1);
                        unDisparo.reiniciar();
                        puntaje = puntaje + 10;

                        // Si el jugador gano
                        if(puntaje == numInvasores * 10){
                            pausado = true;
                            puntaje = 0;
                            vidas = 3;
                            prepararNivel();
                        }
                    }
                }
            }
        }
        //Se elige aleatoriamente quien realiza el ataque kamikaze
        if(aleatorio.nextInt(200)==0){//Algun invasor quiere hacer un ataque Kamikaze? si es cero entonces si
            idInvasor=aleatorio.nextInt(numInvasores);
            if(!invasores[idInvasor].getModoKamikaze()&&invasores[idInvasor].getVisibilidad()){
                invasores[idInvasor].setModoKamikaze();
                soundPool.play(caidaInvasor,1,1,0,0,1);
            }
        }

        // Verifica si las balas de los invasores colisionan con la nave del jugador
        for(int i = 0; i < disparosInvasores.length; i++){
            if(disparosInvasores[i].getEstado()){
                if(RectF.intersects(jugador.getRect(), disparosInvasores[i].getRect())){
                    disparosInvasores[i].reiniciar();
                    vidas --;
                    soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);

                    // Se verifica si el jugador no tiene mas vidas
                    if(vidas == 0){
                        pausado = true;
                        vidas = 3;
                        puntaje = 0;
                        prepararNivel();
                    }
                }
            }
        }
        //Verifica si un invasor colisiona con la nave del jugador
        for(int i=0; i<numInvasores;i++){
            if(invasores[i].getModoKamikaze()&& invasores[i].getVisibilidad()){//Si el invasor esta en modo kamikaze y vivo
                if(RectF.intersects(jugador.getRect(), invasores[i].getRect())){
                    invasores[i].setInvisible();//El invasor muere
                    vidas --;//El jugador pierde una vida
                    soundPool.play(playerExplodeID, 1, 1, 0, 0, 1);

                    // Se verifica si el jugador no tiene mas vidas
                    if(vidas == 0){
                        pausado = true;
                        vidas = 3;
                        puntaje = 0;
                        prepararNivel();
                    }
                }
            }
        }
    }
    private void crearInvasores(){
        //Se define a los invasores
        //j: fila i: columna
        numInvasores = 0;
        //Se definen los dos invasores de la primer fila
        invasores[0]= new Invasor(context,0,2,pantallaX,pantallaY,3);
        numInvasores++;
        invasores[1]= new Invasor(context,0,3,pantallaX,pantallaY,3);
        numInvasores++;
        //Se definen los 4 invasores de la segunda fila
        invasores[2]= new Invasor(context,1,1,pantallaX,pantallaY,4);
        numInvasores++;
        invasores[3]= new Invasor(context,1,2,pantallaX,pantallaY,4);
        numInvasores++;
        invasores[4]= new Invasor(context,1,3,pantallaX,pantallaY,4);
        numInvasores++;
        invasores[5]= new Invasor(context,1,4,pantallaX,pantallaY,4);
        numInvasores++;
        //Se definen los invasores de la tercer fila
        for(int i=0;i<6;i++){
            invasores[numInvasores]= new Invasor(context,2,i,pantallaX,pantallaY,1);
            numInvasores++;
        }
        for(int i= 0; i < 2; i ++ ){
            for(int j = 0; j < 6; j ++ ){
                invasores[numInvasores] = new Invasor(context, (i+3), j, pantallaX, pantallaY,2);
                numInvasores ++;
            }
        }
    }

    public void pause() {
        //Si el activity es pausado o detenido, detiene al hilo
        jugando = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }
    public void resume() {
        //Si el activity es reiniciado, inicia al hilo
        //
        jugando = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // El jugador toco la pantalla
            case MotionEvent.ACTION_DOWN:

                pausado = false;
                if(motionEvent.getY() > pantallaY - pantallaY / 8) {
                    if (motionEvent.getX() > pantallaX / 2) {
                        //si el jugador toca la parte derecha de la mitad de la pantalla, entonces la nave se mueve hacia la derecha
                        jugador.setMovementState(jugador.DERECHA);
                    } else {
                        //sino se mueve a la izquierda
                        jugador.setMovementState(jugador.IZQUIERDA);
                    }

                }

                if(motionEvent.getY() < pantallaY - pantallaY / 8) {
                    // Codigo que permite disparar al jugador
                    if(unDisparo.disparo(jugador.getX()+
                            jugador.getLongitud()/2,jugador.getY(),unDisparo.ARRIBA)){
                        soundPool.play(disparoNave, 1, 1, 0, 0, 1);
                    }
                }
                break;

            // El jugador saca el dedo de la pantalla
            case MotionEvent.ACTION_UP:

                if(motionEvent.getY() > pantallaY - pantallaY / 8) {
                    jugador.setMovementState(jugador.PARAR);
                }

                break;

        }

        return true;
    }
}
