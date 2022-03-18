package com.example.writeout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ViewPagerAdapter extends FragmentStateAdapter {
    private  String[] Titles={"Your Articles","Other Articles","Favourites"};
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new YourArticleFragment();
            case 1:
                return new OtherArticlesFragment();
            case 2:
                return new FavouritesFragment();
        }

        return new YourArticleFragment();
    }

    @Override
    public int getItemCount() {
        return Titles.length;
    }
}
