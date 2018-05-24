package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;

public class Bonus extends ArtifactObject {
    public enum BonusKind {
        Speedster(0, 5, 0),
        HealthPotion(0, 0, 5),
        RedBull(2, 2, 2),;

        final double damage;
        final int health;
        final int speed;

        private BonusKind(double damage, int speed, int health) {
            this.damage = damage;
            this.speed = speed;
            this.health = health;
        }
    }

    private final BonusKind kind;

    public Bonus(Bitmap image, BonusKind kind) {
        super();
        this.kind = kind;
    }

    @Override
    public String getDescription() {
        return null;
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

    public BonusKind kind() {
        return kind;
    }
}
