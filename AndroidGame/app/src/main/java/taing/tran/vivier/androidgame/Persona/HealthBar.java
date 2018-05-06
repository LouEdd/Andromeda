package taing.tran.vivier.androidgame.Persona;


import android.widget.ProgressBar;

public class HealthBar implements MyObserver{
    private int health;
    private ProgressBar progressBar;

    public HealthBar(int health){
        this.health = health;
    }

    public int getHealth(){
        return health;
    }

    @Override
    public void update(int health) {
        this.health = health;
    }
}
