package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import taing.tran.vivier.androidgame.Persona.Character;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    private int hp;
    private ProgressBar progressBar;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().getName(), "onCreate: main activity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);
        textView = (TextView) findViewById(R.id.textViewHP);
        gameView = (GameView) findViewById(R.id.gameView);
        textView.setTextColor(Color.BLACK);
        //hp = gameView.getHealth();
        //textView.setText(String.valueOf(hp));
        hp = gameView.getHealth();
        textView.setText(String.valueOf(hp));
        progressBar.setProgress(hp);

    }

    @Override
    protected void onResume() {
        super.onResume();
        textView.setText(String.valueOf(hp));
        progressBar.setProgress(hp);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            Log.d(getClass().getName(), "onActivityResult: Quizz/DuelActivity result ok");
            /*
            Character ennemy = (Character) data.getParcelableExtra("ennemy");
            hp = ennemy.getHealth();
            gameView.setHealth(hp);
            */
            ArrayList<Character> players = data.getParcelableArrayListExtra("players");
            if(players.get(0).isIA()) {
                gameView.processEnd(false);
                return;
            }
            gameView.setPlayers(players);
            gameView.setHealth(players.get(0).getHealth());
        }
        /*if(requestCode == 3) {
            Intent newIntent = new Intent(this, DuelActivity.class);
            Bundle bundle = getIntent().getExtras();
            if(bundle == null){
                Log.e("WHAT", "ahahah");
            }
            newIntent.putExtras(getIntent().getExtras());
            startActivityForResult(newIntent, 2);
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
