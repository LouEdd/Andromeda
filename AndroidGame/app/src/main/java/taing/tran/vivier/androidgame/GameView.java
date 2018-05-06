package taing.tran.vivier.androidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import taing.tran.vivier.androidgame.Persona.Character;
import taing.tran.vivier.androidgame.Persona.HealthBar;
import taing.tran.vivier.androidgame.battlefield.Battlefield;

/**
 * Created by Eddy on 23/04/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Character character;
    private SurfaceHolder surfaceHolder;
    private HealthBar healthBar;
    private Battlefield battlefield;
    private Paint transparentPaint;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        init();
    }

    public GameView(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        init();
    }

    private void init() {
        gameThread = new GameThread(this);
        character = new Character(this.getContext());
        healthBar = new HealthBar(character.getHealth());
        character.registerObserver(healthBar);
        battlefield = Battlefield.createDefault(this.getContext());
        transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transparentPaint.setColor(Color.TRANSPARENT);
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        transparentPaint.setAntiAlias(true);

        this.setZOrderOnTop(true);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(battlefield.getBattlefieldImg());
        } else {
            setBackground(battlefield.getBattlefieldImg());
        }
        getHolder().setFormat(PixelFormat.TRANSPARENT);
    }

    public void doDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        canvas.drawPaint(transparentPaint); // efface la couche précédente sans effacer le background
        battlefield.draw(canvas);
        character.draw(canvas);
    }

    public void update() {
        this.character.update();
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
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int currentX = (int) event.getX();
            int currentY = (int) event.getY();
            int moveX = currentX - character.getX();
            int moveY = currentY - character.getY();
            this.character.move(moveX, moveY, currentX, currentY);
        }
        return true;
    }


    public int getHealth() {
        return healthBar.getHealth();
    }

    public void setHealth(int health) {
        character.setHealth(health);
    }


}
