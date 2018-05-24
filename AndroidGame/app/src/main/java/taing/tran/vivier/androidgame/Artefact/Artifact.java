package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public interface Artifact {
    public Bitmap getBitmap();
    public void drawArtifact(Canvas canvas);
    public void move(int xMove, int yMove);
    public Rect getRect();
    public String getDescription();
    public int speed();
    public int health();
    public double damage();
}
