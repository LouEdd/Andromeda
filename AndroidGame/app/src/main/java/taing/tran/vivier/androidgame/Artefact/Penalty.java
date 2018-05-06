package taing.tran.vivier.androidgame.Artefact;

public class Penalty implements Artifact {
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

    public Penalty(PenaltyKind kind) {
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

    public PenaltyKind kind() {
        return kind;
    }
}