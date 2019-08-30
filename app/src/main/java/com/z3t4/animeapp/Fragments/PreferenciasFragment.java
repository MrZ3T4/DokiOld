package com.z3t4.animeapp.Fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.z3t4.animeapp.R;

public class PreferenciasFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }


}
