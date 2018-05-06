package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import taing.tran.vivier.androidgame.Artefact.Artefact;
import taing.tran.vivier.androidgame.Artefact.Sword;


public class DuelActivity extends AppCompatActivity{
    private int hp;
    private ProgressBar progressBar;
    private Artefact artefact;
    private ArrayList<Artefact> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duel_activity);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        final Button button = (Button) findViewById(R.id.buttonDuel);
        final ListView listView = (ListView) findViewById(R.id.artefact_list);

        list.add(new Sword(10, this));
        list.add(new Sword(20, this));
        list.add(new Sword(30, this));

        ArrayAdapter<Artefact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                artefact = (Artefact) adapterView.getItemAtPosition(i);
            }
        });

        Intent intent = getIntent();
        hp = intent.getIntExtra("hp", 0);
        progressBar.setProgress(hp);

    }

    public void onClickButtonDuel(View view) {
        if (artefact == null) {
            return;
        }

        hp -= artefact.getDamage();
    //    onFragmentInteraction(hp);
        progressBar.setProgress(hp);
        if (hp <= 0) {
            Intent intent = new Intent();
            intent.putExtra("hp", hp);
            setResult(2, intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("hp", hp);
        setResult(2, intent);
        finish();
    }
}
