package taing.tran.vivier.androidgame.battlefield.obstacle;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by v.vivier on 06/05/2018.
 */

public interface Obstacle {
    public void draw(Canvas canvas);
    public Rect getRect();
}
