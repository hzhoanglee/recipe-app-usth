package vn.edu.usth.demoapp.adapter_ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.edu.usth.demoapp.fragment_ui.CategoryFragment;
import vn.edu.usth.demoapp.fragment_ui.ExploreFragment;
import vn.edu.usth.demoapp.fragment_ui.FavoriteFragment;
import vn.edu.usth.demoapp.fragment_ui.HomeFragment;

public class ViewPagerAdapter2 extends FragmentStateAdapter {

    public ViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 0:
                return new HomeFragment();
            case 1:
                return new ExploreFragment();
            case 2:
                return new CategoryFragment();
            case 3:
                return new FavoriteFragment();
            default:
                return new HomeFragment();
        }
    }

    public String getTitle(int position)
    {
        switch (position)
        {
            case 0:
                return "Home";
            case 1:
                return "Explore";
            case 2:
                return "Category";
            case 3:
                return "Favorite";
            default:
                return "Home";
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
