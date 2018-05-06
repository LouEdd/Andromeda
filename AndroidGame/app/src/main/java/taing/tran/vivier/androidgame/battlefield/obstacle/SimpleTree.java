package taing.tran.vivier.androidgame.battlefield.obstacle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import taing.tran.vivier.androidgame.R;

/**
 * Created by v.vivier on 06/05/2018.
 */

public class SimpleTree extends AbstractObstacle {
    private SimpleTree(int x, int y, Bitmap obstacleImg) {
        super(x, y, obstacleImg);
    }

    public static SimpleTree create(int x, int y, Context context) {
        Bitmap obstacleImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.decoration_texture_simpletree);
        return new SimpleTree(x, y, obstacleImg);
    }
}
