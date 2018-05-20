package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import taing.tran.vivier.androidgame.Quizz.QuizzActivity;


public class DuelActivity extends AppCompatActivity{
    private int hp;
    private Character fighter1;
    private Character ennemy;
    private ProgressBar progressBar;
    private Artifact artefact;
    private ArrayList<Artifact> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duel_activity);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        final Button button = (Button) findViewById(R.id.buttonDuel);
        final ListView listView = (ListView) findViewById(R.id.artefact_list);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.knife);

        list.add(new Weapon(image, Weapon.WeaponKind.Dagger));
        list.add(new Weapon(image, Weapon.WeaponKind.Spear));
        list.add(new Weapon(image, Weapon.WeaponKind.Sword));

        ArtifactAdapter artifactAdapter = new ArtifactAdapter(this, R.layout.listweapon, list);

        //ArrayAdapter<Artifact> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(artifactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                artefact = (Artifact) adapterView.getItemAtPosition(i);
            }
        });

        Intent intent = getIntent();
        fighter1 = ((Character) intent.getParcelableExtra("fighter1"));
        ennemy = (Character) intent.getParcelableExtra("ennemy");
        hp = ennemy.getHealth();
        //hp = intent.getIntExtra("hp", 0);
        progressBar.setProgress(hp);

    }

    public void onClickButtonDuel(View view) {
        if (artefact == null) {
            return;
        }

        hp -= artefact.damage();
        progressBar.setProgress(hp);
        ennemy.setHealth(hp);
        if (hp <= 0) {

            Log.e("INFOR", "Duel");

            Intent intent = new Intent();
            intent.putExtra("fighter1", fighter1);
            intent.putExtra("ennemy", ennemy);
            setResult(4, intent);
            //fighter1.isFighting(false);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("fighter1", fighter1 );
        intent.putExtra("ennemy", ennemy );
        setResult(2, intent);
        finish();
    }
}