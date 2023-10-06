package vn.edu.usth.demoapp.adapter_ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import vn.edu.usth.demoapp.fragment_ui.PhotoFragment;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.object_ui.Photo;

public class PhotoAdapter extends FragmentStateAdapter {

    private List<Food> foodList;

    public PhotoAdapter(@NonNull FragmentActivity fragmentActivity, List<Food> foodList) {
        super(fragmentActivity);
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Food food = foodList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objFood", food);
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public int getItemCount() {
        if (foodList != null) {
            return foodList.size();
        }
        return 0;
    }
}
