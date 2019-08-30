package com.z3t4.animeapp.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.z3t4.animeapp.Tabs.AnimeTabFragment;
import com.z3t4.animeapp.Tabs.OvaTabFragment;
import com.z3t4.animeapp.Tabs.PeliculasTabFragment;
import com.z3t4.animeapp.R;

import java.util.ArrayList;
import java.util.List;

public class ExplorarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.y_explorar_fragment, container, false);
       /* ViewPager viewPager = (ViewPager)view.findViewById(R.id.explorar_view_pager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.explorar_tab);
        tabs.setupWithViewPager(viewPager);
*/
        return view;
    }











    // Add Fragments to Tabs
/*    private void setupViewPager(ViewPager viewPager) {


        RecientesFragment.Adapter adapter = new RecientesFragment.Adapter(getChildFragmentManager());
        adapter.addFragment(new AnimeTabFragment(), "Anime");
        adapter.addFragment(new OvaTabFragment(), "OVA");
        adapter.addFragment(new PeliculasTabFragment(), "Películas");

        viewPager.setAdapter(adapter);

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    } */
}
