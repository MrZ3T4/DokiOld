package com.z3t4.animeapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.z3t4.animeapp.Fragments.BottomSheetDialog;
import com.z3t4.animeapp.Fragments.PreferenciasFragment;
import com.z3t4.animeapp.R;

public class Preferencias extends Activity {

    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenedor_preferencias);
        getFragmentManager().beginTransaction().replace(R.id.contenedor_preferencias_fragment, new PreferenciasFragment()).commit();

        SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
    }

}
