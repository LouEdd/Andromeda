package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class ArtifactObject {

    protected Bitmap image;

    protected int x;
    protected int y;

    public ArtifactObject(Bitmap image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;

    }

    public void drawArtifact(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
