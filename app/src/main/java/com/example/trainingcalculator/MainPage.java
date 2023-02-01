package com.example.trainingcalculator;


import android.annotation.SuppressLint;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jetbrains.annotations.NotNull;

public class MainPage extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener
{
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
            bottomNavigationView = findViewById(R.id.bottomnav);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            loadFragment(new LapTime());
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.lapCalc:
                fragment = new LapTime();
                break;
            case R.id.timePaceDist:
                fragment = new TimePaceDist();
                break;
            case R.id.timer:
                fragment = new Timer();
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }
    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.parent, fragment).commit();
    }
}