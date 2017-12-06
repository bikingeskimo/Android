package com.example.oving4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Fragment2.ObjectSelectedListener {

    private ArrayList<Object> objects;
    private Fragment1 fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objects = new ArrayList<>();
        objects.add(new Object("Dangerous weapon", "Use with caution!", R.drawable.obj2));
        objects.add(new Object("Impossible toy", "This toy is impossible.", R.drawable.obj3));
        objects.add(new Object("Yellow egg", "Unknown species, might be dangerous", R.drawable.obj4));
        objects.add(new Object("Bonus", "You have completed this mindgame, well done", R.drawable.obj1));

        Fragment2 listFragment = (Fragment2) getFragmentManager().findFragmentById(R.id.fragment2_);
        listFragment.setListAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                objects
        ));

        fragment1 = (Fragment1) getFragmentManager().findFragmentById(R.id.fragment1_);
    }
    @Override
    protected void onStart() {
        super.onStart();
        fragment1.update(new Object());
    }

    @Override
    public void OnObjectSelected(int index) {
        fragment1.update(objects.get(index));
    }
}
