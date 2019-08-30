package com.z3t4.animeapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.tbouron.shakedetector.library.ShakeDetector;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.z3t4.animeapp.Fragments.BottomSheetDialog;
import com.z3t4.animeapp.Fragments.ExplorarFragment;
import com.z3t4.animeapp.Fragments.FavoritosFragment;
import com.z3t4.animeapp.Fragments.NoticiasFragment;
import com.z3t4.animeapp.R;
import com.z3t4.animeapp.Fragments.RecientesFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton Buscar;
    private BottomNavigationView bottomNavigationView;
    private Button botonExplorar;
    private com.airbnb.lottie.LottieAnimationView confetti;
    private Dialog dialog;
    private CardView reportarError;
    private TextView tvLogo;
    private ImageView imgLogo;
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        confetti = findViewById(R.id.dialog_confetti);
        reportarError = findViewById(R.id.reportar_error);

        ShakeDetector.create(MainActivity.this, new ShakeDetector.OnShakeListener() {
            @Override
            public void OnShake() {
                confetti.playAnimation();
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        dialog = new Dialog(MainActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.setContentView(R.layout.bug_dialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                }
            }; handler.postDelayed(runnable, 1100);

            }});
        ShakeDetector.updateConfiguration(5.0f, 3);

        this.Fragment();
        this.Toolbar();
        this.BottomNavigationBar();
        this.Objetos();

    }

    public void Reportar(View view){
        Toast.makeText(MainActivity.this, "Reporte en desarrollo :D", Toast.LENGTH_LONG).show();
        dialog.dismiss();}

    @Override
    protected void onResume() {
        ShakeDetector.start();
        super.onResume();
    }

    @Override
    protected void onStop() {
        ShakeDetector.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        ShakeDetector.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

        } else {
            Toast.makeText(getBaseContext(), "Presiona de nuevo para salir",
                    Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    Animation animationshow = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_show);
                    animationshow.setFillAfter(true);
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_hide);
                    animation.setFillAfter(true);

                    switch (menuItem.getItemId()) {

                        case R.id.recientes_fragment:
                            toolbar.setElevation(0);
                            Buscar.show();
                            selectedFragment = new RecientesFragment();
                            break;
                        case R.id.guardados_fragment:
                            toolbar.setElevation(0);
                            selectedFragment = new FavoritosFragment();
                            Buscar.hide();
                            break;
                        case R.id.explorar_fragment:
                            toolbar.setElevation(4);
                            selectedFragment = new ExplorarFragment();
                            Buscar.show();
                            break;
                        case R.id.noticias_fragment:
                            toolbar.setElevation(4);
                            selectedFragment = new NoticiasFragment();
                            Buscar.hide();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.contenedor_fragment, selectedFragment).commit();

                    return true;
                }
            };

    private BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener
            = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {

        }
    };


    public void TestActivity(View view) {
        Intent buscarFAB = new Intent(MainActivity.this, Busqueda.class);
        startActivity(buscarFAB);
    }

    public void AgregarColeccion(View view) {
        bottomNavigationView.setSelectedItemId(R.id.explorar_fragment);
    }

    public void Descargas(View view) {
        Intent descargas = new Intent(MainActivity.this, Descargas.class);
        startActivity(descargas);
    }

    public void BottomNavigationBar() {
        //Declara y enlaza por id el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);
    }

    public void Objetos() {
        // Enlaza el FloationButton
        Buscar = findViewById(R.id.FAB_Buscar);
        botonExplorar = findViewById(R.id.explorar_boton);
        tvLogo = findViewById(R.id.text_logo_toolbar);
        imgLogo = findViewById(R.id.logo_toolbar);
    }

    public void Toolbar() {
        toolbar = findViewById(R.id.toolbar); //Llama a la toolbar del XML y la enlaza con el objeto en Main
        setSupportActionBar(toolbar); //Establece la toolbar como actionbar
        getSupportActionBar().setDisplayShowTitleEnabled(false); //Oculta el titulo por defecto
    }

    public void Fragment() {
        //Reemplaza el fragment con id contenedor dentro de Main creando una instancia con la clase RecentFragment
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace
                (R.id.contenedor_fragment, new RecientesFragment()).commit();


    }

    public void Animeinfo(View view) {
        Intent intent1 = new Intent(MainActivity.this, InformacionDelAnime.class);
        startActivity(intent1);
    }

    public void Settings(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.show(getSupportFragmentManager().beginTransaction(), "exampleBottomSheet");
    }


    public void Preferences(View view) {
        Intent intent1 = new Intent(MainActivity.this, Preferencias.class);
        startActivity(intent1);
    }

    public void Hide() {
        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
    }

    public void Show() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }
}

