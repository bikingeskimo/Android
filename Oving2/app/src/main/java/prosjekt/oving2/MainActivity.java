package prosjekt.oving2;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String forste = this.getString(R.string.forste);
        final int forst = Integer.parseInt(forste);

        final String andre = getString(R.string.andre);
        final int andr = Integer.parseInt(andre);

        final String addSvar = getString(R.string.addSvar);
        final int aSvar = Integer.parseInt(addSvar);

        final String multSvar = getString(R.string.multSvar);
        final int mSvar = Integer.parseInt(multSvar);

        final Context c = getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = getApplicationContext();
                Oppg1 oppg1 = new Oppg1(c);
                try{
                    final EditText upperLimitText = (EditText) findViewById(R.id.upperlimitText);
                    oppg1.printRandom(Integer.parseInt(upperLimitText.getText().toString()));
                    //finish();
                    Log.i("onCreate","oppg1 er avsluttet");
                }catch (Exception e){
                    Toast.makeText(c, "Fyll inn Øvre grense riktig.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final EditText svar = (EditText) findViewById(R.id.svarText); //fetching text inside textbox

        Button a = (Button) findViewById(R.id.button);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onClick", "Multiplikasjon er valgt av brukeren");
                Log.i("onClick", "forste tall er: " + forst);
                Log.i("onClick", "andre tall er: " + andr);
                Oppg2 oppg = new Oppg2(c);
                Oppg1 rand = new Oppg1(c);
                TextView number1 = (TextView) findViewById(R.id.textView);
                TextView number2 = (TextView) findViewById(R.id.textView2);

                try {
                    int multiplySvar = oppg.multiply(Integer.parseInt(number1.getText().toString()),
                                                Integer.parseInt(number2.getText().toString()));


                    if (Integer.parseInt(svar.getText().toString()) == multiplySvar){
                        Toast.makeText(c, getString(R.string.correct), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(c, getString(R.string.wrong) + " " + multiplySvar, Toast.LENGTH_SHORT).show();
                    }
                    final EditText upperLimitText = (EditText) findViewById(R.id.upperlimitText);

                    number1.setText(String.valueOf(rand.getRandomNum(Integer.parseInt(upperLimitText.getText().toString()))));
                    number2.setText(String.valueOf(rand.getRandomNum(Integer.parseInt(upperLimitText.getText().toString()))));

                }catch(Exception e){
                    Toast.makeText(c, "Please insert a number in Svar!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Button b = (Button) findViewById(R.id.button2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onClick", "Addering er valgt av brukeren");
                Log.i("onClick", "forste tall er: " + forst);
                Log.i("onClick", "andre tall er: " + andr);


                TextView number1 = (TextView) findViewById(R.id.textView);
                TextView number2 = (TextView) findViewById(R.id.textView2);
                Oppg2 oppg = new Oppg2(c);
                Oppg1 rand = new Oppg1(c);
                try {
                    int additionSvar = oppg.add(Integer.parseInt(number1.getText().toString()),
                            Integer.parseInt(number2.getText().toString()));


                    if (Integer.parseInt(svar.getText().toString()) == additionSvar) {
                        Toast.makeText(c, getString(R.string.correct), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(c, getString(R.string.wrong) + " " + additionSvar, Toast.LENGTH_SHORT).show();
                    }

                    final EditText upperLimitText = (EditText) findViewById(R.id.upperlimitText);

                    number1.setText(String.valueOf(rand.getRandomNum(Integer.parseInt(upperLimitText.getText().toString()))));
                    number2.setText(String.valueOf(rand.getRandomNum(Integer.parseInt(upperLimitText.getText().toString()))));
                }catch (Exception e){
                    Toast.makeText(c, "Please insert a number in Svar!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
