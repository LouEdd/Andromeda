package taing.tran.vivier.androidgame.Persona;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import taing.tran.vivier.androidgame.Artefact.Artifact;
import taing.tran.vivier.androidgame.R;

/**
 * Created by Eddy on 23/04/2018.
 */

public class Character implements Parcelable {
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
    //private final Context context;
    private long lastDrawNanoTime = -1;
    private int health;
    private int damage;
    private final ArrayList<Artifact> inventory;
    //private final Paint paint;
    private Rect rect;
    private boolean fighting;
    private boolean ia;


    public Character(Context context, boolean ia){
        //this.context = context;
        this.x = 10;
        this.y = 10;
        this.health = 100;
        this.damage = 10;
        this.inventory = new ArrayList<>();
        //this.paint = new Paint();
        this.rect = new Rect(x, y, width + x, height + y);
        this.ia = ia;

    }

    protected Character(Parcel in) {
        widthScreen = in.readInt();
        heightScreen = in.readInt();
        height = in.readInt();
        width = in.readInt();
        x = in.readInt();
        y = in.readInt();
        targetX = in.readInt();
        targetY = in.readInt();
        speed = in.readFloat();
        moveX = in.readInt();
        moveY = in.readInt();
        lastDrawNanoTime = in.readLong();
        health = in.readInt();
        damage = in.readInt();
        rect = in.readParcelable(Rect.class.getClassLoader());
        fighting = in.readByte() != 0;
        inventory = in.readArrayList(null);

    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    public int getHealth(){
        return (health < 0)?0:health;
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

        rect.set(x, y, width + x, height + y);

        // Pour s'arreter ou le player a toucher avec son doigt
        if(x <= targetX && x >= targetX - width){
            moveX = 0;
        }

        if(y <= targetY && y >= targetY - height){
            moveY = 0;
        }





    }


    public void move(int moveX, int moveY, int targetX, int targetY){
        this.moveX = moveX;
        this.moveY = moveY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void move(int xMove, int yMove) {
        this.x += xMove;
        this.y += yMove;
        this.rect.set(x, y, width + x, height + y);
    }



    public BitmapDrawable setImage(Context context, int resource, int w, int h){
        Drawable drawable = context.getResources().getDrawable(resource);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, w, h, true));
    }



    public void draw(Canvas canvas) {
        /*if(img==null){
            return;
        }*/
        if(health > 0){

            Paint paint = new Paint();
            paint.setTextSize(32);
            paint.setColor(Color.WHITE);
            //canvas.drawBitmap(img.getBitmap(), x ,y , null);
            canvas.drawText(health + "", x, y, paint);
            canvas.drawRect(rect, paint);

        }

        this.lastDrawNanoTime = System.nanoTime();
    }

    public void resize(int i1, int i2) {
        widthScreen = i1;
        heightScreen = i2;
        width = 50;
        height = 50;
        //img = setImage(context, R.drawable.chun, width, height);
    }

    public void setY(int y) {
        this.y = y;
        this.rect.set(x, y, width + x, height + y);
    }

    public void setX(int x) {
        this.x = x;
        this.rect.set(x, y, width + x, height + y);
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public Rect getRect() {
        return rect;
    }

    public void stopMoving() {
        this.moveX = 0;
        this.moveY = 0;
    }

    public boolean isFighting() {
        return fighting;
    }

    public void isFighting(boolean isFighting) {
        this.fighting = isFighting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(widthScreen);
        dest.writeInt(heightScreen);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(targetX);
        dest.writeInt(targetY);
        dest.writeFloat(speed);
        dest.writeInt(moveX);
        dest.writeInt(moveY);
        dest.writeLong(lastDrawNanoTime);
        dest.writeInt(health);
        dest.writeInt(damage);
        dest.writeParcelable(rect, flags);
        dest.writeByte((byte) (fighting ? 1 : 0));
        dest.writeList(inventory);
    }

    public boolean isIA() {
        return this.ia;
    }
}
