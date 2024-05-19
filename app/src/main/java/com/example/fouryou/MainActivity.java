package com.example.fouryou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment frag1;
    private write_diaryFragment frag2;
    private my_profile frag3;


    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_menu);

        frag1 = new HomeFragment();
        frag2 = new write_diaryFragment();
        frag3 = new my_profile();

        setFrag(0);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.tab_home) {
                    setFrag(0);
                } else if (itemId == R.id.tab_diary) {
                    setFrag(1);
                } else if (itemId == R.id.tab_profile) {
                    setFrag(2);
                }
                return true;
            }
        });
    }
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.frame_main, frag1);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.frame_main, frag2);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.frame_main, frag3);
                ft.commit();
                break;
        }
    }
}
