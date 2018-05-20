package taing.tran.vivier.androidgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import taing.tran.vivier.androidgame.Persona.Character;
import taing.tran.vivier.androidgame.Quizz.Quizz;
import taing.tran.vivier.androidgame.Quizz.QuizzActivity;
import taing.tran.vivier.androidgame.battlefield.Battlefield;
import taing.tran.vivier.androidgame.battlefield.obstacle.Obstacle;

/**
 * Created by Eddy on 23/04/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Character character;
    private Character secondCharacter;
    private ArrayList<Character> ennemies;
    private SurfaceHolder surfaceHolder;
    private Battlefield battlefield;
    private Paint transparentPaint;
    private Context context;
    private Activity activity;
    private float previousXtouch;
    private float previousYtouch;
    private int previousEvent;

    public GameView(Context context) {
        super(context);
        this.context = context;
        this.activity = (Activity) context;
        getHolder().addCallback(this);
        init();
    }

    public GameView(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.activity = (Activity) context;
        getHolder().addCallback(this);
        init();
    }

    private void init() {
        gameThread = new GameThread(this);
        character = new Character(this.getContext());
        ennemies = new ArrayList<>();
        secondCharacter = new Character(this.getContext());
        secondCharacter.setX(500);
        secondCharacter.setY(500);
        ennemies.add(secondCharacter);
        battlefield = Battlefield.createDefault(this.getContext());
    }

    public void doDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        battlefield.draw(canvas);
        character.draw(canvas);
        secondCharacter.draw(canvas);
    }

    public void update() {
        this.character.update();
        this.secondCharacter.update();


        for (Character ennemy : ennemies) {
            if (Rect.intersects(character.getRect(), ennemy.getRect()) && !character.isFighting()) {
                character.stopMoving();
                character.isFighting(true);
/*
            Intent newIntent = new Intent(activity, DuelActivity.class);
            newIntent.putExtra("fighter1", character);
            newIntent.putExtra("ennemy", secondCharacter);
            activity.startActivityForResult(newIntent, 2);
*/              Log.e("INFOR", "GameView");
                Intent quizzIntent = new Intent(activity, QuizzActivity.class);
                quizzIntent.putExtra("fighter1", character);
                quizzIntent.putExtra("ennemy", ennemy);
                activity.startActivityForResult(quizzIntent, 3);
                character.stopMoving();




            }


        }


        for (Obstacle obstacle : battlefield.getObstacles()) {
            if (Rect.intersects(obstacle.getRect(), character.getRect())) {
                character.stopMoving();
            }
        }

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (gameThread.getState() == Thread.State.TERMINATED) {
            gameThread = new GameThread(this);
        }
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        character.resize(i1, i2);
        secondCharacter.resize(i1, i2);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (previousEvent != MotionEvent.ACTION_MOVE) {
                    int currentX = (int) event.getX();
                    int currentY = (int) event.getY();
                    int moveX = currentX - character.getX();
                    int moveY = currentY - character.getY();
                    character.move(moveX, moveY, currentX, currentY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int xMove = (int) (x - previousXtouch);
                int yMove = (int) (y - previousYtouch);
                // séparation en deux IF pour permettre de bouger dans un un des deux sens même si l'autre sens est illégal
                if (character.getX() + xMove > 0 && character.getRect().right + xMove < getWidth()) {
                    battlefield.move(xMove, 0);
                    character.move(xMove, 0);
                    secondCharacter.move(xMove, 0);
                }
                if (character.getY() + yMove > 0 && character.getRect().bottom + yMove < getHeight()) {
                    battlefield.move(0, yMove);
                    character.move(0, yMove);
                    secondCharacter.move(0, yMove);
                }
                break;
        }
        previousXtouch = x;
        previousYtouch = y;
        previousEvent = event.getAction();
        return true;
    }


    public int getHealth() {
        return character.getHealth();
    }

    public void setHealth(int health) {
        secondCharacter.setHealth(health);
    }

    public Character getCharacter() {
        return character;
    }

    public ArrayList<Character> getEnnemies() {
        return ennemies;
    }
}
