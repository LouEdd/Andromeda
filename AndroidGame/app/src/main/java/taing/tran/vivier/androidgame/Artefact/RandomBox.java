package taing.tran.vivier.androidgame.Artefact;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public static RandomBox createShield(int x, int y, Context context){
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.box);
        Shield shield = new Shield(Shield.ShieldKind.values()[new Random().nextInt(Shield.ShieldKind.values().length)]);
        return new RandomBox(image, x, y);
    }

    @Override
    public String getDescription() {
        return null;
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
