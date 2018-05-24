package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class ArtifactObject implements Artifact {

    private Bitmap image;
    private Rect rect;
    private Point point;

    public ArtifactObject() {
    }

    public ArtifactObject(Bitmap image, int x, int y) {
        this.image = image;
        setPosition(x, y);
    }

    public void setPosition(int x, int y){
        this.point = new Point(x, y);
        this.rect = new Rect(x, y, image.getWidth() + x, image.getHeight() + y);
    }

    public void drawArtifact(Canvas canvas) {
        canvas.drawBitmap(image, point.x, point.y, null);
    }

    public Bitmap getBitmap() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
