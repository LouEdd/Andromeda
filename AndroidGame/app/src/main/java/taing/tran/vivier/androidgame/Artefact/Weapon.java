package taing.tran.vivier.androidgame.Artefact;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import taing.tran.vivier.androidgame.DuelActivity;
import taing.tran.vivier.androidgame.R;

public class Weapon extends ArtifactObject implements Parcelable{
    public enum WeaponKind {
        Sword(15, 0, 0),
        Dagger(8, 5, 0),
        Spear(10, 0, 5),
        ;

        final double damage;
        final int health;
        final int speed;

        private WeaponKind(double damage, int speed, int health) {
            double scarcity = Math.random() * 5;
            this.damage = damage + 1.25*scarcity;
            this.speed = speed;
            this.health = health;

        }
    }

    private final WeaponKind kind;

    public Weapon(Activity activity, WeaponKind kind){
        super();
        this.kind = kind;
        if(kind == WeaponKind.Sword) {
            super.setImage(BitmapFactory.decodeResource(activity.getResources(), R.drawable.sword2));
        }
        if(kind == WeaponKind.Dagger) {
            super.setImage(BitmapFactory.decodeResource(activity.getResources(), R.drawable.knife2));
        }
        if(kind == WeaponKind.Spear) {
            super.setImage(BitmapFactory.decodeResource(activity.getResources(), R.drawable.spear2));
        }
    }

    @Override
    public String getDescription() {
        return this.kind.name()  + " : " + " damage : " + this.damage();
    }

    public int speed() {
        return kind.speed;
    }

    public int health() {
        return kind.health;
    }

    public double damage() {
        return kind.damage;
    }

    @Override
    public Artifact getArtifact() {
        return this;
    }

    public WeaponKind kind() {
        return kind;
    }

    @Override
    public String toString(){
        return this.kind.name() + " " + (int) damage();
    }

    protected Weapon(Parcel in) {
        kind = (WeaponKind) in.readValue(WeaponKind.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(kind);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Weapon> CREATOR = new Parcelable.Creator<Weapon>() {
        @Override
        public Weapon createFromParcel(Parcel in) {
            return new Weapon(in);
        }

        @Override
        public Weapon[] newArray(int size) {
            return new Weapon[size];
        }
    };

    @Override
    public Bitmap getBitmap() {
        return super.getBitmap();
    }
}
