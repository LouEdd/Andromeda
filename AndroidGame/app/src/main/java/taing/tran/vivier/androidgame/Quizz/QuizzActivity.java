package taing.tran.vivier.androidgame.Quizz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import taing.tran.vivier.androidgame.DuelActivity;
import taing.tran.vivier.androidgame.R;

public class QuizzActivity extends AppCompatActivity {
    private AssetManager assetManager;
    private ProgressBar progressBar;
    private MediaPlayer mediaPlayer;
    private TextView textView;
    private Quizz quizz;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private int success = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz);

        //mediaPlayer = MediaPlayer.create(this, R.raw.lovesick);
        //mediaPlayer.start();
        //progressBar = (ProgressBar) findViewById(R.id.quizzprogress);
        assetManager = getAssets();
        textView = (TextView) findViewById(R.id.quizztext);
        b1 = (Button) findViewById(R.id.quizzb1);
        b2 = (Button) findViewById(R.id.quizzb2);
        b3 = (Button) findViewById(R.id.quizzb3);
        b4 = (Button) findViewById(R.id.quizzb4);

        List<String> list = getOneRandomAsset();
        quizz = Quizz.createQuizz(list);
        Uri mp3 = Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + quizz.getResource());
        mediaPlayer = MediaPlayer.create(this, mp3);
        mediaPlayer.start();

        textView.setText(quizz.getQuestion());
        b1.setText(quizz.getAnswers().get(0));
        b2.setText(quizz.getAnswers().get(1));
        b3.setText(quizz.getAnswers().get(2));
        b4.setText(quizz.getAnswers().get(3));
    }


    public void onClickButtonQuizz(View view) {
        Button button = (Button) view;
        String answer = button.getText().toString().toString();
        mediaPlayer.stop();
        if (answer.equals(quizz.getAnswer())) {
            Toast.makeText(this, "GREAT!",
                    Toast.LENGTH_LONG).show();
            success = 1;
        } else {
            Toast.makeText(this, "NOPE!",
                    Toast.LENGTH_LONG).show();
            success = 0;
        }



        Log.e("INFOR", "Quizz");
        Intent intent = new Intent(this, DuelActivity.class);
        intent.putExtra("bonus", success);
        intent.putExtras(getIntent().getExtras());
        Log.d("COUCOU", getIntent().getExtras().toString());

        startActivityForResult(intent, 4);
        finish();

    }

    private List<String> getOneRandomAsset() {
        List<String> list = new ArrayList<>();
        List<String> listAsset = new ArrayList<>();
        try {
            list = getAssetsList();
            InputStream inputStream = assetManager.open("quizz/" + list.get(0));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listAsset.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return listAsset;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("INFOR", "QuizzBefore " + requestCode + " "  + resultCode);
        if(requestCode == 4){
            Log.e("INFOR", "Quizz Result");
            setResult(2, data);
            finish();

        }
        if(resultCode == 5){
            setResult(3, data);
            finish();
        }


    }

    private List<String> getAssetsList() throws IOException {
        String[] files = assetManager.list("quizz");
        List<String> list = new ArrayList<>(Arrays.asList(files));
        Collections.shuffle(list);
        return list;
    }

}
