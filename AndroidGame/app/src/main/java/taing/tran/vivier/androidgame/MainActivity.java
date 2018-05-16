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

import taing.tran.vivier.androidgame.Persona.Character;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    private int hp;
    private ProgressBar progressBar;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Log.e("HEHE", hp + "");
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
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            Character ennemy = (Character) data.getParcelableExtra("ennemy");
            hp = ennemy.getHealth();
            gameView.setHealth(hp);
        }
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
