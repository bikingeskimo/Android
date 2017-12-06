package no.hist.itfag.books;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Roger on 20.10.2017.
 */

public class PreferenceClass extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
