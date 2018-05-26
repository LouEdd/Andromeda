package taing.tran.vivier.androidgame;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by v.vivier on 26/05/2018.
 */

public class GameOverActivity extends AppCompatActivity {
    TextView endTextView;
    boolean win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().getName(), "onCreate: GameOverActivity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_message);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        win = getIntent().getBooleanExtra("win", false);
        endTextView = (TextView) findViewById(R.id.endText);
        endTextView.setTextColor(Color.BLACK);
        String message;
        if(win) {
            message = "Damn you are so strong\nYOU WIN !";
        } else {
            message = "OMG, noob\nYOU LOSE !";
        }
        endTextView.setText(message);
    }
}
