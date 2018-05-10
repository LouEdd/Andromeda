package taing.tran.vivier.androidgame.Artefact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import taing.tran.vivier.androidgame.R;

/**
 * Created by v.vivier on 09/05/2018.
 */

public class ArtifactAdapter extends ArrayAdapter<Artifact> {
    private ArrayList<Artifact> list = new ArrayList<>();
    private Context context;

    public ArtifactAdapter(Context context, int resource, ArrayList<Artifact> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.listweapon, parent, false);

        Artifact artifact = list.get(position);
        TextView textView = (TextView) convertView.findViewById(R.id.list_weapon_text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_weapon_image);

        textView.setText(artifact.toString());
        imageView.setImageBitmap(artifact.getBitmap());



        return convertView;
    }
}
