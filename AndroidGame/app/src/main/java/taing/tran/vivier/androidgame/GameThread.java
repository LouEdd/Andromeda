package taing.tran.vivier.androidgame;

import android.graphics.Canvas;

/**
 * Created by Eddy on 23/04/2018.
 */

public class GameThread extends Thread {
    private final static int FPS = 30;
    private final static int ST = 1000/FPS;
    private final GameView view;
    private boolean running = false;

    public GameThread(GameView view){
        this.view = view;
    }

    public void setRunning(boolean run){
        running = run;
    }

    @Override
    public void run(){
        long startTime;
        long sleepTime;
        while(running){
            startTime = System.currentTimeMillis();
            synchronized (view.getHolder()){
                view.update();
            }
            Canvas canvas = null;
            try{
                canvas = view.getHolder().lockCanvas();
                synchronized (view.getHolder()){
                    view.doDraw(canvas);
                }
            }finally {
                if(canvas != null){
                    view.getHolder().unlockCanvasAndPost(canvas);
                }
            }

            sleepTime = ST - (System.currentTimeMillis() - startTime);
            try{
                if(sleepTime >= 0){
                    sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
