package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class ArtifactObject implements Artifact {

    private Bitmap image;

    private static final int MAP_WIDTH = 640;
    private static final int MAP_HEIGHT = 480;
    protected float x;
    protected float y;

    public ArtifactObject(Bitmap image) {
        this.image = image;
        this.x = (float) Math.random() * MAP_WIDTH;
        this.y = (float )Math.random() * MAP_HEIGHT;

    }

    public void drawArtifact(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Bitmap getBitmap(){
        return image;
    }
}
