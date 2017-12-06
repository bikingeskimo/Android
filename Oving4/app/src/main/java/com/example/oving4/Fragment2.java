package com.example.oving4;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


/**
 * Created by Roger on 29.09.2017.
 */

public class Fragment2 extends ListFragment {

    interface ObjectSelectedListener {
        void OnObjectSelected(int index);
    }

    private ObjectSelectedListener listener;
    private int selectedItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menuoption_back, menu);
        inflater.inflate(R.menu.menuoption_next, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_back:
                selectedItem--;
                if(selectedItem < 0) selectedItem = getListAdapter().getCount() -1;
                listener.OnObjectSelected(selectedItem);
                return true;

            case R.id.menu_next:
                selectedItem++;
                if(selectedItem >= getListAdapter().getCount()) selectedItem = 0;
                listener.OnObjectSelected(selectedItem);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setScrollbarFadingEnabled(false);
        try {
            listener = (ObjectSelectedListener) getActivity();
        }
        catch(Exception e) {
            throw new ClassCastException("Error");
        }
    }

    @Override
    public void onListItemClick(ListView list, View view, int index, long id) {
        listener.OnObjectSelected(index);
    }
}
