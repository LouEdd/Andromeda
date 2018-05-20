package taing.tran.vivier.androidgame.battlefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import taing.tran.vivier.androidgame.R;
import taing.tran.vivier.androidgame.battlefield.obstacle.Obstacle;
import taing.tran.vivier.androidgame.battlefield.obstacle.SimpleTree;

/**
 * Created by v.vivier on 06/05/2018.
 */

public class Battlefield {
    private static final int DEFAULT_RADIUS = 8000;

    private int x;
    private int y;
    private final int radius;
    private final BitmapDrawable battlefieldImg;
    private int safeZoneX;
    private int safeZoneY;
    private int safeZoneRadius;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private int canvasHeight;
    private int canvasWidth;
    private Bitmap part;
    private Paint paint = new Paint();
    private Bitmap scaled;

    private Battlefield(int radius, BitmapDrawable battlefieldImg) {
        this.safeZoneRadius = this.radius = radius;
        this.safeZoneX = this.x = 0;
        this.safeZoneY = this.y = 0;
        this.battlefieldImg = battlefieldImg;
    }

    public static Battlefield createDefault(Context context) {
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.field_texture_grass);
        BitmapDrawable battlefieldImg = new BitmapDrawable(context.getResources(), tmp);
        battlefieldImg.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Battlefield newBf = new Battlefield(DEFAULT_RADIUS, battlefieldImg);

        newBf.obstacles.add(SimpleTree.create(100,100, context));
        newBf.obstacles.add(SimpleTree.create(200,100, context));
        newBf.obstacles.add(SimpleTree.create(400,300, context));

        return newBf;
    }

    public static Battlefield createRandom(Context context){
        // TODO
        return createDefault(context);
    }

    private void init() {
        this.scaled = Bitmap.createScaledBitmap(battlefieldImg.getBitmap(), canvasWidth*2, canvasHeight*2,  false);
        this.part = Bitmap.createBitmap(scaled, x%canvasWidth, y%canvasHeight, canvasWidth, canvasHeight);
        this.paint.setShader(new BitmapShader(part, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }

    public void draw(Canvas canvas) {
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        if(part != null) {
            canvas.drawBitmap(part, 0, 0, paint);
        } else {
            init();
        }
        for (Obstacle obs : obstacles) {
            obs.draw(canvas);
        }
    }

    public void move(int xMove, int yMove) {
        this.part = Bitmap.createBitmap(scaled, x%canvasWidth, y%canvasHeight, canvasWidth, canvasHeight);
        this.paint.setShader(new BitmapShader(part, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        if((this.x -= xMove) < 0) {
            this.x = canvasWidth;
        }
        if((this.y -= yMove) < 0) {
            this.y = canvasHeight;
        }
        for (Obstacle o : obstacles) {
            o.move(xMove, yMove);
        }
    }

    public BitmapDrawable getBattlefieldImg() {
        return battlefieldImg;
    }

    public List<Obstacle> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }
}
