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
    //private Character fighter1;
    //private Character ennemy;
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

        ArtifactAdapter artifactAdapter = new ArtifactAdapter(this, R.layout.listweapon, list);

        //ArrayAdapter<Artifact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(artifactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                artefact1 = (Artifact) adapterView.getItemAtPosition(i);
            }
        });

        Intent intent = getIntent();
        players = intent.getParcelableArrayListExtra("players");
        //fighter1 = ((Character) intent.getParcelableExtra("fighter1"));
        //ennemy = (Character) intent.getParcelableExtra("ennemy");
        indexFighter1 = intent.getIntExtra("indexFighter1", -1);
        indexFighter2 = intent.getIntExtra("indexFighter2", -1);
        if(indexFighter1 == -1 || indexFighter2 == -1) {
            onBackPressed();
        }
        hp1 = players.get(indexFighter1).getHealth();
        hp2 = players.get(indexFighter2).getHealth();
        //hp = intent.getIntExtra("hp", 0);
        pbFighter1.setProgress(hp1);
        pbFighter2.setProgress(hp2);
    }

    public void onClickButtonDuel(View view) {
        if (artefact1 != null) {
            hp2 -= artefact1.damage();
            pbFighter2.setProgress(hp2);
            players.get(indexFighter2).setHealth(hp2);
            if(hp2 <= 0) {
                Log.i(getClass().getName(), "onClickButtonDuel: Fighter 2 died");
                players.remove(indexFighter2);
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("players", players);
                setResult(4, intent);
                finish();
            }
        }
        if(artefact2 != null) {
            hp1 -= artefact2.damage();
            pbFighter2.setProgress(hp2);
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
        /*
        if (hp <= 0) {

            Log.e("INFOR", "Duel");

            Intent intent = new Intent();
            intent.putExtra("fighter1", fighter1);
            intent.putExtra("ennemy", ennemy);
            setResult(4, intent);
            //fighter1.isFighting(false);
            finish();
        }*/
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("players", players);
        setResult(4, intent);
        finish();
    }
}