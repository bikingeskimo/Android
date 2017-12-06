package no.hist.itfag.books;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends Activity {
    private DatabaseManager db;
    private String selected_color = "selected_color";
    private String default_color = "black";
    private ArrayList<String> list;
    boolean fileFound, authorsSelected;
    LinearLayout layout;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        layout = (LinearLayout) findViewById(R.id.linear_layout);

        try {
            db = new DatabaseManager(this);
            db.clean();
            long id = db.insert("Bud Kurniawan","Android Application Development: A Beginners Tutorioal");
            id = db.insert("Mildrid Ljosland", "Programmering i C++");
            id = db.insert("Else Lervik", "Programmering i C++");
            id = db.insert("Mildrid Ljosland", "Algoritmer og datastrukturer");
            id = db.insert("Helge Hafting", "Algoritmer og datastrukturer");

            String title = null, author = null;
            int lineNr = 0;
            BufferedReader reader;
            try {
                final InputStream file = getAssets().open("test.txt");
                reader = new BufferedReader(new InputStreamReader(file));
                String line = reader.readLine();
                while (line != null) {
                    Log.d("StackOverflow", line);
                    if  ((lineNr & 1) == 1){
                        title = line;
                    }else if  ((lineNr & 1) == 0) {
                        author = line;
                    }
                    if (title != null && author != null) {
                        db.insert(title, author);
                    }
                    lineNr++;
                    line = reader.readLine();
                }
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.book_list);
        listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );
        listView.setAdapter(listAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setBackgroundColor();
    }

    public void setBackgroundColor() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selected = sharedPreferences.getString(selected_color, default_color);
        Log.i("setBGColor()", "selected: " + selected);
        if(selected.equals(default_color)) {
            layout.setBackgroundColor(Color.BLACK);
        } else {
            selected = selected.substring(2);
            long color = Long.parseLong(selected, 16);
            Log.i("inside else", "selected: " + selected);
            layout.setBackgroundColor((int) color);
        }
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
        if (id == R.id.authors_menu_item) {
            list.clear();
            list.addAll(db.getAllAuthors());
            listAdapter.notifyDataSetChanged();
            authorsSelected = true;
            return true;
        }

        else if(id == R.id.books_menu_item) {
            list.clear();
            list.addAll(db.getAllBooks());
            listAdapter.notifyDataSetChanged();
            authorsSelected = false;
            return true;
        }

        else if(id == R.id.settings_menu_item) {
            startActivity(new Intent(this, Settings.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
