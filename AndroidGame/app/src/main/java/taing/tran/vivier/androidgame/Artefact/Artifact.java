package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;

public interface Artifact {
    public double damage();
    public int speed();
    public int health();
    public float getX();
    public float getY();
    public Bitmap getBitmap();
}
