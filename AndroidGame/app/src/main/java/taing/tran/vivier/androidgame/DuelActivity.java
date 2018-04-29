package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import taing.tran.vivier.androidgame.Persona.HealthBarFragment;


public class DuelActivity extends AppCompatActivity implements OnFragmentListener {
    private int hp;
    HealthBarFragment healthBarFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duel_activity);
        healthBarFragment = (HealthBarFragment) getFragmentManager().findFragmentById(R.id.fragmentation);

        final Button button = (Button) findViewById(R.id.buttonDuel);


        Intent intent = getIntent();
        hp = intent.getIntExtra("hp", 0);




    }

    public void onClickButtonDuel(View view){
        hp -= 10;
        onFragmentInteraction(hp);
        if(hp <= 0){
            Intent intent = new Intent();
            intent.putExtra("hp", hp);
            setResult(2, intent);
            finish();
        }

    }


    @Override
    public void onFragmentInteraction(int hp) {
        //HealthBarFragment healthBarFragment = (HealthBarFragment) getFragmentManager().findFragmentById(R.id.fragmentation);
        healthBarFragment.setHealth(hp);
        healthBarFragment.setText(hp + "");
    }
}
