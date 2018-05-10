package taing.tran.vivier.androidgame.Artefact;


import android.graphics.Bitmap;

public class Weapon extends ArtifactObject{
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

    public Weapon(Bitmap image, WeaponKind kind){
        super(image);
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
    public WeaponKind kind() {
        return kind;
    }

    @Override
    public String toString(){
        return this.kind.name() + " " + (int) damage();
    }
}
