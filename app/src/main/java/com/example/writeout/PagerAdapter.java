package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    private final ArrayList<String> fragmentTitles=new ArrayList<>();
    public PagerAdapter(@NonNull FragmentManager fm,int behaviour) {
        super(fm,behaviour);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
    public void addFragment(Fragment fragment,String Title){
        fragmentArrayList.add(fragment);
        fragmentTitles.add(Title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}
