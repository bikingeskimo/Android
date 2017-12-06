package prosjekt.oving2;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.Random;


/**
 * Created by Roger on 13.09.2017.
 */

class Oppg1 extends Activity{
    public int max=100;

    private Context c;
    Random rand = new Random();

    Oppg1(Context context){
        c = context;
    }

    int getRandomNum(int newMax){

        max = newMax;

        return rand.nextInt(max) +1;
    }

    void printRandom(int newMax){
        int r = getRandomNum(newMax);
        Toast.makeText(c, "Ditt random nummer er: " + r, Toast.LENGTH_LONG).show();
    }
}
