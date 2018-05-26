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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import taing.tran.vivier.androidgame.Artefact.Artifact;
import taing.tran.vivier.androidgame.Persona.Character;
import taing.tran.vivier.androidgame.Quizz.QuizzActivity;
import taing.tran.vivier.androidgame.battlefield.Battlefield;
import taing.tran.vivier.androidgame.battlefield.obstacle.Obstacle;

/**
 * Created by Eddy on 23/04/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int NUMBER_OF_IA = 1;
    private GameThread gameThread;
    //private Character character;
    //private Character secondCharacter;
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
        //character = new Character(this.getContext());
        //secondCharacter = new Character(this.getContext());
        //secondCharacter.setX(500);
        //secondCharacter.setY(500);
        Log.i(getClass().getName(), "init: Génération du personnage principal");
        Character mainPlayer = new Character(this.getContext());
        Log.i(getClass().getName(), "init: Génération des IA");
        List<Character> iaPlayers = new ArrayList<>(NUMBER_OF_IA);
        for(int i=0; i<NUMBER_OF_IA; i++) {
            Character ia = new Character(this.getContext());
            //ia.setX((int)(Math.random()%500) - 1000);
            //ia.setY((int)(Math.random()%500) - 1000);
            ia.setX(500);
            ia.setY(500);
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
        //character.draw(canvas);
        //secondCharacter.draw(canvas);
    }

    public void update() {
        if(players == null) {
            return;
        }
        Iterator<Character> it = players.iterator();
        Character p;
        while(it.hasNext()) {
            p = it.next();
            if(p.getHealth() == 0) {
                it.remove();
            }
            p.update();
        }
        //this.character.update();
        //this.secondCharacter.update();

        for (int i=1; i<players.size(); i++) {
            if (Rect.intersects(players.get(0).getRect(), players.get(i).getRect()) && !players.get(0).isFighting()) {
                Log.i(getClass().getName(), "update: collision detected");
                players.get(0).stopMoving();
                players.get(i).stopMoving();
                players.get(0).isFighting(true);
                players.get(i).isFighting(true);
/*
            Intent newIntent = new Intent(activity, DuelActivity.class);
            newIntent.putExtra("fighter1", character);
            newIntent.putExtra("ennemy", secondCharacter);
            activity.startActivityForResult(newIntent, 2);
*/
                Intent quizzIntent = new Intent(activity, QuizzActivity.class);
                quizzIntent.putParcelableArrayListExtra("players", players);
                quizzIntent.putExtra("indexFighter1", 0);
                quizzIntent.putExtra("indexFighter2", i);
                //quizzIntent.putExtra("fighter1", character);
                //quizzIntent.putExtra("ennemy", ennemy);
                activity.startActivityForResult(quizzIntent, 3);
                //character.stopMoving();
            }
        }

        for (Obstacle obstacle : battlefield.getObstacles()) {
            if (Rect.intersects(obstacle.getRect(), players.get(0).getRect())) {
                players.get(0).stopMoving();
            }
        }


        for (Artifact artifact : battlefield.getArtifacts()) {
            if (Rect.intersects(artifact.getRect(), players.get(0).getRect())) {
                artifacts.add(artifact);
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
        //character.resize(i1, i2);
        //secondCharacter.resize(i1, i2);
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
                    players.get(0).move(moveX, moveY, currentX, currentY);
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
                    //character.move(xMove, 0);
                    //secondCharacter.move(xMove, 0);
                }
                if (players.get(0).getY() + yMove > 0 && players.get(0).getRect().bottom + yMove < getHeight()) {
                    battlefield.move(0, yMove);
                    for(Character p : players) {
                        p.move(0, yMove);
                    }
                    //character.move(0, yMove);
                    //secondCharacter.move(0, yMove);
                }
                break;
        }
        previousXtouch = x;
        previousYtouch = y;
        previousEvent = event.getAction();
        return true;
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
}
