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

import java.util.ArrayList;

import taing.tran.vivier.androidgame.Artefact.Artifact;
import taing.tran.vivier.androidgame.Artefact.Weapon;


public class DuelActivity extends AppCompatActivity{
    private int hp;
    private ProgressBar progressBar;
    private Artifact artefact;
    private ArrayList<Artifact> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duel_activity);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        final Button button = (Button) findViewById(R.id.buttonDuel);
        final ListView listView = (ListView) findViewById(R.id.artefact_list);

        list.add(new Weapon(Weapon.WeaponKind.Dagger));
        list.add(new Weapon(Weapon.WeaponKind.Spear));
        list.add(new Weapon(Weapon.WeaponKind.Sword));

        ArrayAdapter<Artifact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                artefact = (Artifact) adapterView.getItemAtPosition(i);
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

        hp -= artefact.damage();
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
