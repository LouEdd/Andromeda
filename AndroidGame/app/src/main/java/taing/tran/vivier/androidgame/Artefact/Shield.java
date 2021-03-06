package taing.tran.vivier.androidgame.Artefact;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import taing.tran.vivier.androidgame.R;

public class Shield extends ArtifactObject implements Parcelable {
    protected Shield(Parcel in) {
        //this.kind = ShieldKind.valueOf(in.readString());
        kind = (ShieldKind) in.readValue(ShieldKind.class.getClassLoader());
    }

    public static final Creator<Shield> CREATOR = new Creator<Shield>() {
        @Override
        public Shield createFromParcel(Parcel in) {
            return new Shield(in);
        }

        @Override
        public Shield[] newArray(int size) {
            return new Shield[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeSerializable(kind);
        parcel.writeValue(kind);
    }

    public enum ShieldKind {
        TORNYSHIELD(1, 5, 5),
        VALKYRJASHIELD(1, 2, 10),
        STONEBUCKLER(3, -5, 15),
        ;

        final double damage;
        final int health;
        final int speed;

        private ShieldKind(double damage, int speed, int health) {
            this.damage = damage;
            this.speed = speed;
            this.health = health;
        }
    }

    private final ShieldKind kind;
    private Bitmap image;

    public Shield(Context context, ShieldKind kind) {
        super();
        this.kind = kind;
        if(kind == ShieldKind.VALKYRJASHIELD) {
            super.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.valkyrjashield));
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.valkyrjashield);
        }
        if(kind == ShieldKind.STONEBUCKLER) {
            super.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.stonebuckler));
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.stonebuckler);
        }
        if(kind == ShieldKind.TORNYSHIELD) {
            super.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.tornyshield));
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.tornyshield);
        }
    }
    public Shield(ShieldKind kind){
        super();
        this.kind = kind;
    }

    @Override
    public String getDescription() {
        return this.kind.name() + " : "  + " defense : " + this.health();
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

    public ShieldKind kind() {
        return kind;
    }

    @Override
    public String toString(){
        return this.kind.name() + " " + (int) damage();
    }

    @Override
    public Bitmap getBitmap() {
        return super.getBitmap();
    }


}
