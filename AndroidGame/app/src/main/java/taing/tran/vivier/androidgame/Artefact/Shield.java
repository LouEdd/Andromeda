package taing.tran.vivier.androidgame.Artefact;

import android.app.Activity;
import android.graphics.BitmapFactory;

import taing.tran.vivier.androidgame.R;

public class Shield extends ArtifactObject {
    public enum ShieldKind {
        TornyShield(0, 5, 5),
        ValkyrjaShield(0, 2, 10),
        StoneBuckler(3, -5, 15),
        ;

        final double damage;
        final int health;
        final int speed;
        String name;

        private ShieldKind(double damage, int speed, int health) {
            this.damage = damage;
            this.speed = speed;
            this.health = health;
        }
    }

    private final ShieldKind kind;

    public Shield(Activity activity, ShieldKind kind) {
        super();
        this.kind = kind;
        if(kind == ShieldKind.ValkyrjaShield) {
            super.setImage(BitmapFactory.decodeResource(activity.getResources(), R.drawable.valkyrjashield));
        }
        if(kind == ShieldKind.StoneBuckler) {
            super.setImage(BitmapFactory.decodeResource(activity.getResources(), R.drawable.stonebuckler));
        }
        if(kind == ShieldKind.TornyShield) {
            super.setImage(BitmapFactory.decodeResource(activity.getResources(), R.drawable.tornyshield));
        }
    }
    public Shield(ShieldKind kind){
        super();
        this.kind = kind;
    }

    @Override
    public String getDescription() {
        if(this.equals(ShieldKind.StoneBuckler))
            return "Stone Buckler : " + " speed : " + this.speed() + " health : " + this.health() + " damage : " + this.damage();
        return "";
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

    public ShieldKind kind() {
        return kind;
    }
}
