package com.example.fouryou;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;




/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TextView textViewDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);




        TabLayout tabLayout = view.findViewById(R.id.tablayout);

        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_home_content, new Calendar_Page())
                    .commit();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        selectedFragment = new Calendar_Page();
                        break;
                    case 1:
                        selectedFragment = new Forest();
                        break;
                    case 2:
                        selectedFragment = new music_recommend();
                        break;
                }
                if (selectedFragment != null) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.fragment_home_content, selectedFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 탭이 선택 해제될 때의 동작 (필요한 경우)
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 탭이 다시 선택될 때의 동작 (필요한 경우)
            }
        });

        return view;
    }
}