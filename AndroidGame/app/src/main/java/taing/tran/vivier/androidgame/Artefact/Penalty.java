package taing.tran.vivier.androidgame.Artefact;

import android.graphics.Bitmap;

public class Penalty extends ArtifactObject{
    public enum PenaltyKind {
        Trap(0, -5, 0),
        Mine(0, 0, -15),
        Envenom(-1, -1, -1),

        ;

        final double damage;
        final int health;
        final int speed;

        private PenaltyKind(double damage, int speed, int health) {
            this.damage = damage;
            this.speed = speed;
            this.health = health;
        }
    }

    private final PenaltyKind kind;

    public Penalty(Bitmap image, PenaltyKind kind) {
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

    @Override
    public Artifact getArtifact() {
        return this;
    }

    public PenaltyKind kind() {
        return kind;
    }
}
