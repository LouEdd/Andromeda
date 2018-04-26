package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DuelActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duel_activity);


        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView textView = (TextView) findViewById(R.id.hp);
        final Button button = (Button) findViewById(R.id.buttonDuel);
        final

        Intent intent = getIntent();

        final ArrayList<Parcelable> list = intent.getParcelableArrayListExtra("list");
        int hp = intent.getIntExtra("hp", 0);

        progressBar.setMax(hp);
        progressBar.setProgress(hp);



    }

    public void onClickButtonDuel(View view){
        Toast.makeText(this, "CLICKED", Toast.LENGTH_LONG).show();
    }
}
