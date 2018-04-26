package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import taing.tran.vivier.androidgame.Persona.Character;

public class MainActivity extends AppCompatActivity {
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        gameView = (GameView) findViewById(R.id.gameView);
        final Button button = (Button) findViewById(R.id.buttonMain);




    }

    public void onClickButtonMain(View view){
        Intent intent = new Intent(this, DuelActivity.class);
        intent.putExtra("hp", gameView.getHealth());
        startActivity(intent);
    }

}
