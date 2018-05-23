package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;

public class Shield extends ArtifactObject {
    public enum ShieldKind {
        TornyShield(0, 5, 5),
        ValkyrjaShield(0, 2, 10),
        StoneBuckler(3, -5, 15),
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

    public Shield(Bitmap image, ShieldKind kind) {
        super();
        this.kind = kind;
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
