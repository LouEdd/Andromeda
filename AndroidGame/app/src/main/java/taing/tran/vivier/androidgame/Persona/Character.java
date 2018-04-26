package taing.tran.vivier.androidgame.Persona;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import taing.tran.vivier.androidgame.Artefact.Artefact;
import taing.tran.vivier.androidgame.R;

/**
 * Created by Eddy on 23/04/2018.
 */

public class Character {
    private BitmapDrawable img;
    private int widthScreen;
    private int heightScreen;
    private int height;
    private int width;
    private int x;
    private int y;
    private int targetX;
    private int targetY;
    private float speed = 1f;
    private int moveX = 0;
    private int moveY = 0;
    private final Context context;
    private long lastDrawNanoTime = -1;
    private int health;
    private int damage;
    private final ArrayList<Artefact> inventory;

    public Character(Context context){
        this.context = context;
        this.x = 10;
        this.y = 10;
        this.health = 100;
        this.damage = 10;
        this.inventory = new ArrayList<>();


    }

    public int getHealth(){
        return health;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void update() {
        long now = System.nanoTime();
        if (lastDrawNanoTime == -1) {
            lastDrawNanoTime = now;
        }

        int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);
        float distance = speed * deltaTime;

        double moveLength = Math.sqrt(moveX * moveX + moveY * moveY);
        this.x = x + (int) (distance * moveX / moveLength);
        this.y = y + (int) (distance * moveY / moveLength);


        if(x < 0){
            x = 0;
        }else if(x > widthScreen - width){
            x = widthScreen - width;
        }

        if(y < 0){
            y = 0;
        }else if (y > heightScreen - height){
            y = heightScreen - height;
        }

        if(x <= targetX + width && x >= targetX - width){
            moveX = 0;
        }

        if(y <= targetY + height && y >= targetY - height){
            moveY = 0;
        }



    }


    public void move(int moveX, int moveY, int targetX, int targetY){
        this.moveX = moveX;
        this.moveY = moveY;
        this.targetX = targetX;
        this.targetY = targetY;
    }



    public BitmapDrawable setImage(Context context, int resource, int w, int h){
        Drawable drawable = context.getResources().getDrawable(resource);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }



    public void draw(Canvas canvas) {
        if(img==null){
            return;
        }
        canvas.drawBitmap(img.getBitmap(), x ,y , null);
        this.lastDrawNanoTime = System.nanoTime();
    }

    public void resize(int i1, int i2) {
        widthScreen = i1;
        heightScreen = i2;
        width = widthScreen/10;
        height = heightScreen/10;
        img = setImage(context, R.drawable.chun, width, height);
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
