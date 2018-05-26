package taing.tran.vivier.androidgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import taing.tran.vivier.androidgame.Artefact.Artifact;
import taing.tran.vivier.androidgame.Persona.Character;
import taing.tran.vivier.androidgame.Persona.Segment;
import taing.tran.vivier.androidgame.Quizz.QuizzActivity;
import taing.tran.vivier.androidgame.battlefield.Battlefield;
import taing.tran.vivier.androidgame.battlefield.obstacle.Obstacle;

/**
 * Created by Eddy on 23/04/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int NUMBER_OF_IA = 5;
    private GameThread gameThread;
    private ArrayList<Character> players;   // first is the player, others are IA
    private ArrayList<Artifact> artifacts = new ArrayList<>();
    private SurfaceHolder surfaceHolder;
    private Battlefield battlefield;
    private Paint transparentPaint;
    private Context context;
    private Activity activity;
    private float previousXtouch;
    private float previousYtouch;
    private int previousEvent;
    private boolean firstDraw = true;
    private int currentX;
    private int currentY;
    private boolean canMove = true;



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

        Log.i(getClass().getName(), "init: Génération du personnage principal");
        Character mainPlayer = new Character(this.getContext(), false);
        mainPlayer.setX(1000);
        mainPlayer.setY(1000);
        Log.i(getClass().getName(), "init: Génération des IA");
        List<Character> iaPlayers = new ArrayList<>(NUMBER_OF_IA);
        for(int i=0; i<NUMBER_OF_IA; i++) {
            Character ia = new Character(this.getContext(), true);
            ia.setX((int)(Math.random()* 1000) );
            ia.setY((int)(Math.random()* 1000));
            iaPlayers.add(ia);
        }
        // Remplissage de la table des joueurs
        players = new ArrayList<>();
        players.add(0, mainPlayer);
        players.addAll(iaPlayers);
        battlefield = Battlefield.createDefault(this.getContext());
        gameThread = new GameThread(this);
    }

    public void doDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        battlefield.draw(canvas);
        for(Character p : players) {
            p.draw(canvas);
        }
        if(firstDraw) {
            // Centrer la caméra au début
            Log.d(getClass().getName(), "doDraw: centrage caméra");
            battlefield.move(-players.get(0).getX(), -players.get(0).getY());
            for(Character p : players) {
                p.move(-players.get(0).getX(), -players.get(0).getY());
            }
            firstDraw = false;
        }
    }

    public void update() {
        if(players == null) {
            return;
        }
        if((players.size() == 1 && !players.get(0).isIA()) || battlefield.safeRect().contains(players.get(0).getRect())) {
            Log.d(getClass().getName(), "update: rectSafe = " + battlefield.safeRect());
            Log.d(getClass().getName(), "update: rectPlay = " + players.get(0).getRect());

            processEnd(true);
        }
        battlefield.reduceSafeZone();
        Iterator<Character> it = players.iterator();
        Character p;
        while(it.hasNext()) {
            p = it.next();
            if(p.getHealth() == 0) {
                it.remove();
            }
            p.update();
        }

        for (int i=1; i<players.size(); i++) {
            if (Rect.intersects(players.get(0).getRect(), players.get(i).getRect()) && !players.get(0).isFighting()) {
                Log.i(getClass().getName(), "update: collision detected");
                players.get(0).stopMoving();
                players.get(i).stopMoving();
                players.get(0).isFighting(true);
                players.get(i).isFighting(true);

                Intent quizzIntent = new Intent(activity, QuizzActivity.class);
                quizzIntent.putParcelableArrayListExtra("players", players);
                quizzIntent.putExtra("indexFighter1", 0);
                quizzIntent.putExtra("indexFighter2", i);
                activity.startActivityForResult(quizzIntent, 3);
            }
        }
/*
        for (Obstacle obstacle : battlefield.getObstacles()) {
            if (Rect.intersects(obstacle.getRect(), players.get(0).getRect())) {
                players.get(0).stopMoving();
            }
        }
*/

        for (Artifact artifact : battlefield.getArtifacts()) {
            if (Rect.intersects(artifact.getRect(), players.get(0).getRect())) {
                artifacts.add(artifact);
                players.get(0).addInventory(artifact);
                String text = artifact.getDescription();
                //Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
            }
        }
        for (Artifact artifact : artifacts) {
            battlefield.removeArtifact(artifact);
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
        for(Character p : players) {
            p.resize(i1, i2);
        }
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
                    int moveX = currentX - players.get(0).getX();
                    int moveY = currentY - players.get(0).getY();


                    for (Obstacle obstacle : battlefield.getObstacles()) {
                        if(checkObstacle(currentX, currentY, players.get(0).getX(), players.get(0).getY(), obstacle.getRect())){
                            canMove = false;
                        }
                    }

                    if(canMove){
                        players.get(0).move(moveX, moveY, currentX, currentY);
                    }
                    canMove = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int xMove = (int) (x - previousXtouch);
                int yMove = (int) (y - previousYtouch);
                // séparation en deux IF pour permettre de bouger dans un un des deux sens même si l'autre sens est illégal
                if (players.get(0).getX() + xMove > 0 && players.get(0).getRect().right + xMove < getWidth()) {
                    battlefield.move(xMove, 0);
                    for(Character p : players) {
                        p.move(xMove, 0);
                    }

                }
                if (players.get(0).getY() + yMove > 0 && players.get(0).getRect().bottom + yMove < getHeight()) {
                    battlefield.move(0, yMove);
                    for(Character p : players) {
                        p.move(0, yMove);
                    }

                }
                break;
        }
        previousXtouch = x;
        previousYtouch = y;
        previousEvent = event.getAction();
        return true;
    }


    private boolean checkObstacle(int destX, int destY, int startX, int startY, Rect rect){
        Segment segment = new Segment(startX, startY, destX, destY);
        Log.i("HOPE", Segment.intersects2(segment, rect) + "");
        return  Segment.intersects2(segment, rect);
    }


    public int getHealth() {
        return players.get(0).getHealth();
    }

    public void setHealth(int health) {
        players.get(0).setHealth(health);
    }

    public Character getCharacter() {
        return players.get(0);
    }

    public List<Character> getIAs() {
        return players.subList(1, players.size()-1);
    }

    public void setPlayers(ArrayList<Character> players) {
        this.players = players;
    }

    void processEnd(boolean win) {
        Log.d(getClass().getName(), "processEnd: launch GameOverActivity");
        Intent endIntent = new Intent(activity, GameOverActivity.class);
        endIntent.putExtra("win", win);
        activity.startActivity(endIntent);
        gameThread.setRunning(false);
    }
}
