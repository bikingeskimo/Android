package prosjekt.oving2;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Roger on 13.09.2017.
 */

public class Oppg2 extends Activity {
    private Context c;

    Oppg2(Context context){

        c = context;
    }

    public int add(int a,int b){
        return a+b;
    }
    int multiply(int a,int b){
        return a*b;
    }



}
