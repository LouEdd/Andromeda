package taing.tran.vivier.androidgame.Artefact;

import android.os.Parcel;

public class Sword implements Artefact {
    private Bonus bonus;


    public Sword(Bonus bonus){
        this.bonus = bonus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
