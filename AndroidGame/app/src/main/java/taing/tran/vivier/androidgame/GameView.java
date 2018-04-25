package taing.tran.vivier.androidgame;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Eddy on 23/04/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private Character character;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(this);
        character = new Character(this.getContext());
    }

    public void doDraw(Canvas canvas){
        if(canvas == null){
            return;
        }

        canvas.drawColor(Color.BLACK);
        character.draw(canvas);
    }

    public void update(){
        this.character.update();
    }




    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(gameThread.getState() == Thread.State.TERMINATED){
            gameThread = new GameThread(this);
        }
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        character.resize(i1, i2);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry){
            try{
                gameThread.join();
                retry = false;
            }catch (InterruptedException e){

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP){
            int currentX = (int)event.getX();
            int currentY = (int)event.getY();
            int moveX = currentX - character.getX();
            int moveY = currentY - character.getY();
            this.character.move(moveX, moveY, currentX, currentY);
        }
        return true;
    }
}
