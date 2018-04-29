package taing.tran.vivier.androidgame.Persona;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import taing.tran.vivier.androidgame.R;

public class HealthBarFragment extends Fragment {
    private ProgressBar hpBar;
    private TextView hpText;
    private int hp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.healthbar_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        hpText = (TextView) view.findViewById(R.id.hpText);
        hpBar = (ProgressBar) view.findViewById(R.id.hpBar);
        hpText.setText(hp+ "");
        hpBar.setMax(100);
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    public void setHealth(int health) {
        hpBar.setProgress(health);
    }

    public void setText(String health) {
        hpText.setText(health);
    }
}
