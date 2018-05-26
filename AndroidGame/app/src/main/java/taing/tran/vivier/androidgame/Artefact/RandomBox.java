package taing.tran.vivier.androidgame.Artefact;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Random;

import taing.tran.vivier.androidgame.R;

public class RandomBox extends ArtifactObject implements Parcelable{
    private final Artifact artifact;

    private RandomBox(Bitmap image, int x, int y, Artifact artifact) {
        super(image, x, y);
        this.artifact = artifact;
        setPosition(x, y);
    }

    /*public static RandomBox create(int x, int y, Context context) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.box);
        return new RandomBox(image, x, y);
    }*/

    protected RandomBox(Parcel in) {
        artifact = (Artifact) in.readValue(Artifact.class.getClassLoader());
    }

    public static final Creator<RandomBox> CREATOR = new Creator<RandomBox>() {
        @Override
        public RandomBox createFromParcel(Parcel in) {
            return new RandomBox(in);
        }

        @Override
        public RandomBox[] newArray(int size) {
            return new RandomBox[size];
        }
    };

    public static RandomBox createShield(int x, int y, Context context){
        Bitmap image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.treasure), 100, 150, false);

        Shield shield = new Shield(context, Shield.ShieldKind.values()[new Random().nextInt(Shield.ShieldKind.values().length)]);
        return new RandomBox(image, x, y, shield);
    }

    public static RandomBox createWeapon(int x, int y, Context context){
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.treasure);
        Weapon weapon = new Weapon((Activity) context, Weapon.WeaponKind.values()[new Random().nextInt(Weapon.WeaponKind.values().length)]);
        return new RandomBox(image, x, y, weapon);
    }

    public Bitmap getBitmap(){
        return artifact.getBitmap();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(artifact);
    }

    public Artifact getArtifact() {
        return artifact;
    }
}
