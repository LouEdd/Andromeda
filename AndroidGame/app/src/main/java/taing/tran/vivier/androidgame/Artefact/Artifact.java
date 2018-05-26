package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public interface Artifact {
     Bitmap getBitmap();
     void drawArtifact(Canvas canvas);
     void move(int xMove, int yMove);
     Rect getRect();
     String getDescription();
     int speed();
     int health();
     double damage();
     Artifact getArtifact();
}
