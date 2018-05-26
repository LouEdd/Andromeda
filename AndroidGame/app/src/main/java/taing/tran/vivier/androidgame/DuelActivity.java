package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;

import taing.tran.vivier.androidgame.Artefact.Artifact;
import taing.tran.vivier.androidgame.Artefact.ArtifactAdapter;
import taing.tran.vivier.androidgame.Artefact.Weapon;
import taing.tran.vivier.androidgame.Persona.Character;


public class DuelActivity extends AppCompatActivity{
    private int hp1;
    private int hp2;
    private ArrayList<Character> players;
    private int indexFighter1;
    private int indexFighter2;
    private ProgressBar pbFighter1;
    private ProgressBar pbFighter2;
    private Artifact artefact1;
    private Artifact artefact2;
    private ArrayList<Artifact> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duel_activity);

        pbFighter1 = (ProgressBar) findViewById(R.id.progressBar1);
        pbFighter2 = (ProgressBar) findViewById(R.id.progressBar2);

        final Button button = (Button) findViewById(R.id.buttonDuel);
        final ListView listView = (ListView) findViewById(R.id.artefact_list);

        list.add(new Weapon(this, Weapon.WeaponKind.Dagger));
        list.add(new Weapon(this, Weapon.WeaponKind.Spear));
        list.add(new Weapon(this, Weapon.WeaponKind.Sword));

        Intent intent = getIntent();
        players = intent.getParcelableArrayListExtra("players");
        indexFighter1 = intent.getIntExtra("indexFighter1", -1);
        indexFighter2 = intent.getIntExtra("indexFighter2", -1);
        if(indexFighter1 == -1 || indexFighter2 == -1) {
            onBackPressed();
        }
        hp1 = players.get(indexFighter1).getHealth();
        hp2 = players.get(indexFighter2).getHealth();
        pbFighter1.setProgress(hp1);
        pbFighter2.setProgress(hp2);

        ArtifactAdapter artifactAdapter = new ArtifactAdapter(this, R.layout.listweapon, players.get(indexFighter1).getInventory());
        listView.setAdapter(artifactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                artefact1 = (Artifact) adapterView.getItemAtPosition(i);
                Collections.shuffle(list);
                artefact2 = list.get(0);
            }
        });
    }

    public void onClickButtonDuel(View view) {
        if (artefact1 != null) {
            hp2 -= artefact1.damage();
            pbFighter2.setProgress(hp2);
            players.get(indexFighter2).setHealth(hp2);
            if(hp2 <= 0) {
                Log.i(getClass().getName(), "onClickButtonDuel: Fighter 2 died");
                players.remove(indexFighter2);
                players.get(indexFighter1).isFighting(false);
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("players", players);
                setResult(4, intent);
                finish();
            }
        }
        if(artefact2 != null) {
            hp1 -= artefact2.damage();
            pbFighter2.setProgress(hp1);
            players.get(indexFighter1).setHealth(hp1);
            if (hp1 <= 0) {
                Log.i(getClass().getName(), "onClickButtonDuel: Fighter 1 died");
                players.remove(indexFighter1);
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("players", players);
                setResult(4, intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("players", players);
        setResult(4, intent);
        finish();
    }
}