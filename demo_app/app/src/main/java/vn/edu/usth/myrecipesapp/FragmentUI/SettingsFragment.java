package vn.edu.usth.myrecipesapp.FragmentUI;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import vn.edu.usth.myrecipesapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}