package taing.tran.vivier.androidgame.battlefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

import taing.tran.vivier.androidgame.R;
import taing.tran.vivier.androidgame.battlefield.obstacle.Obstacle;
import taing.tran.vivier.androidgame.battlefield.obstacle.SimpleTree;

/**
 * Created by v.vivier on 06/05/2018.
 */

public class Battlefield {
    private static final int DEFAULT_RADIUS = 8000;

    private final int x;
    private final int y;
    private final int radius;
    private final BitmapDrawable battlefieldImg;
    private int safeZoneX;
    private int safeZoneY;
    private int safeZoneRadius;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

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

    public void draw(Canvas canvas) {
        for(Obstacle obs : obstacles) {
            obs.draw(canvas);
        }
    }

    public BitmapDrawable getBattlefieldImg() {
        return battlefieldImg;
    }
}
