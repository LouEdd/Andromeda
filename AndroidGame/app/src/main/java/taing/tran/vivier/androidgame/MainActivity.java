package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import taing.tran.vivier.androidgame.Persona.HealthBar;
import taing.tran.vivier.androidgame.Persona.HealthBarFragment;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    private int hp;
    private ProgressBar progressBar;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);
        textView = (TextView) findViewById(R.id.textViewHP);
        gameView = (GameView) findViewById(R.id.gameView);
        final Button button = (Button) findViewById(R.id.buttonMain);
        textView.setTextColor(Color.BLACK);
        //hp = gameView.getHealth();
        //textView.setText(String.valueOf(hp));
        hp = gameView.getHealth();
        textView.setText(String.valueOf(hp));


        progressBar.setProgress(hp);

    }

    @Override
    protected void onResume(){
        super.onResume();
        hp = gameView.getHealth();
        textView.setText(String.valueOf(hp));
        progressBar.setProgress(hp);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void onClickButtonMain(View view) {
        Intent intent = new Intent(this, DuelActivity.class);
        intent.putExtra("hp", gameView.getHealth());
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            hp = data.getIntExtra("hp", 2);
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
