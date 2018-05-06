package taing.tran.vivier.androidgame.battlefield.obstacle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by v.vivier on 06/05/2018.
 */

abstract class AbstractObstacle implements Obstacle {
    private int x;
    private int y;
    private Bitmap obstacleImg;

    AbstractObstacle(int x, int y, Bitmap obstacleImg) {
        this.x = x;
        this.y = y;
        this.obstacleImg = obstacleImg;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(obstacleImg, x, y, null);
    }
}


