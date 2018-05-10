package taing.tran.vivier.androidgame.battlefield.obstacle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by v.vivier on 06/05/2018.
 */

abstract class AbstractObstacle implements Obstacle {
    private Rect rect;
    private Point point;
    private Bitmap obstacleImg;

    AbstractObstacle(int x, int y, Bitmap obstacleImg) {
        this.point = new Point(x, y);
        this.obstacleImg = obstacleImg;
        this.rect = new Rect(x , y, obstacleImg.getWidth() + x, obstacleImg.getHeight() + y);

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(obstacleImg, point.x, point.y, null);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(rect, paint);
    }

    public Rect getRect() {
        return rect;
    }
}


