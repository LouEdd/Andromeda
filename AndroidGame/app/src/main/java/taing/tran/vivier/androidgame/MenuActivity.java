package taing.tran.vivier.androidgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().getName(), "onCreate: main activity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();

    }

    public void onClickButtonMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
