package taing.tran.vivier.androidgame.Artefact;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import taing.tran.vivier.androidgame.R;

public class RandomBox extends ArtifactObject {
    private RandomBox(Bitmap image, int x, int y) {
        super(image, x, y);
        setPosition(x, y);
    }

    public static RandomBox create(int x, int y, Context context) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.box);
        return new RandomBox(image, x, y);
    }

    @Override
    public int speed() {
        return 0;
    }

    @Override
    public int health() {
        return 0;
    }

    @Override
    public double damage() {
        return 0;
    }
}
