package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface Artifact {
    public Bitmap getBitmap();
    public void drawArtifact(Canvas canvas);
    public int speed();
    public int health();
    public double damage();
}
