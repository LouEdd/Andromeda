package taing.tran.vivier.androidgame.Artefact;


import android.graphics.Bitmap;

public class Weapon extends ArtifactObject implements Artifact {
    public enum WeaponKind {
        Sword(15, 0, 0),
        Dagger(8, 5, 0),
        Spear(10, 0, 5),
        ;

        final int damage;
        final int health;
        final int speed;

        private WeaponKind(int damage, int speed, int health) {
            this.damage = damage;
            this.speed = speed;
            this.health = health;
        }
    }

    /*
     *   Object position
     * */

    private final WeaponKind kind;

    public Weapon(WeaponKind kind){
        this.kind = kind;
    }

    public int speed() {
        return kind.speed;
    }

    public int health() {
        return kind.health;
    }

    public int damage() {
        return kind.damage;
    }
    public WeaponKind kind() {
        return kind;
    }
}
