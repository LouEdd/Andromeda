package taing.tran.vivier.androidgame.battlefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import taing.tran.vivier.androidgame.Artefact.Artifact;
import taing.tran.vivier.androidgame.Artefact.RandomBox;
import taing.tran.vivier.androidgame.R;
import taing.tran.vivier.androidgame.battlefield.obstacle.Obstacle;
import taing.tran.vivier.androidgame.battlefield.obstacle.SimpleTree;

/**
 * Created by v.vivier on 06/05/2018.
 */

public class Battlefield {
    private static final int DEFAULT_RADIUS = 16000;

    private int x;
    private int y;
    private final int radius;
    private final BitmapDrawable battlefieldImg;
    private final Bitmap unsafeZone;
    private final Bitmap safeZone;
    private final Rect safeRect;
    private int safeZoneX;
    private int safeZoneY;
    private int safeZoneRadius;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Artifact> artifacts = new ArrayList<>();
    private int canvasHeight;
    private int canvasWidth;
    private Bitmap part;
    private Paint paint = new Paint();
    private Paint unsafeZonePaint = new Paint();
    private Path circlePath = new Path();
    private Bitmap scaled;

    private Battlefield(int radius, BitmapDrawable battlefieldImg, Bitmap unsafeZone, Bitmap safeZone) {
        this.safeZoneRadius = this.radius = radius;
        this.safeZoneX = this.x = 0;
        this.safeZoneY = this.y = 0;
        this.battlefieldImg = battlefieldImg;
        this.unsafeZone = unsafeZone;
        this.safeZone = safeZone;
        this.safeRect = new Rect(0-safeZone.getWidth()/2, 0-safeZone.getHeight()/2, 0+safeZone.getWidth()/2, 0+safeZone.getHeight()/2);
    }

    public static Battlefield createDefault(Context context) {
        Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.field_texture_grass);
        BitmapDrawable battlefieldImg = new BitmapDrawable(context.getResources(), tmp);
        Bitmap unsafeZone = BitmapFactory.decodeResource(context.getResources(), R.drawable.field_texture_lava);
        battlefieldImg.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Bitmap safeZone = BitmapFactory.decodeResource(context.getResources(), R.drawable.field_texture_safezone);
        Battlefield newBf = new Battlefield(DEFAULT_RADIUS, battlefieldImg, unsafeZone, safeZone);

        newBf.obstacles.add(SimpleTree.create(100,100, context));
        newBf.obstacles.add(SimpleTree.create(200,100, context));
        newBf.obstacles.add(SimpleTree.create(400,300, context));


        newBf.artifacts.add(RandomBox.create(600, 700, context));
        newBf.artifacts.add(RandomBox.create(400, 400, context));
        newBf.artifacts.add(RandomBox.create(700, 200, context));
        newBf.artifacts.add(RandomBox.create(500, 350, context));
        newBf.artifacts.add(RandomBox.create(700, 700, context));

        newBf.artifacts.add(RandomBox.createShield(900, 900, context));


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
        this.unsafeZonePaint.setShader(new BitmapShader(unsafeZone, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        this.circlePath.addCircle(0, 0, radius, Path.Direction.CCW);
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
        for (Artifact art : artifacts) {
            art.drawArtifact(canvas);
        }
        canvas.save();
        circlePath.rewind();
        this.circlePath.addCircle(safeZoneX, safeZoneY, safeZoneRadius, Path.Direction.CCW);
        canvas.clipPath(circlePath, Region.Op.DIFFERENCE);
        canvas.drawPaint(unsafeZonePaint);
        canvas.restore();
        canvas.drawBitmap(safeZone, safeRect.left, safeRect.top, null);
    }

    public void move(int xMove, int yMove) {
        this.part = Bitmap.createBitmap(scaled, x%canvasWidth, y%canvasHeight, canvasWidth, canvasHeight);
        this.paint.setShader(new BitmapShader(part, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        safeZoneX += xMove;
        safeZoneY += yMove;
        safeRect.set(safeRect.left+xMove, safeRect.top+yMove, safeRect.right+xMove, safeRect.bottom+yMove);
        if((this.x -= xMove) < 0) {
            this.x = canvasWidth;
        }
        if((this.y -= yMove) < 0) {
            this.y = canvasHeight;
        }
        for (Obstacle o : obstacles) {
            o.move(xMove, yMove);
        }
        for (Artifact a : artifacts) {
            a.move(xMove, yMove);
        }
    }

    public BitmapDrawable getBattlefieldImg() {
        return battlefieldImg;
    }

    public List<Obstacle> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }
    public List<Artifact> getArtifacts() {
        return Collections.unmodifiableList(artifacts);
    }

    public void removeArtifact(Artifact artifact){
        artifacts.remove(artifact);
    }

    public void reduceSafeZone() {
        if(this.safeZoneRadius > 0) {
            this.safeZoneRadius -= 1;
        }
    }
}
