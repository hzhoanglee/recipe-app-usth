package vn.edu.usth.myrecipesapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.myrecipesapp.FragmentUI.CategoryFragment;
import vn.edu.usth.myrecipesapp.FragmentUI.ExploreFragment;
import vn.edu.usth.myrecipesapp.FragmentUI.FavoriteFragment;
import vn.edu.usth.myrecipesapp.FragmentUI.HomeFragment;

public class ViewPagerAdapter2 extends FragmentStateAdapter {
    public ViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new HomeFragment();
            case 1: return new ExploreFragment();
            case 2: return new CategoryFragment();
            case 3: return new FavoriteFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
