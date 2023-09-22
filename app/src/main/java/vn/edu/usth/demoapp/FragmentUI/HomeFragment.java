package vn.edu.usth.demoapp.FragmentUI;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import vn.edu.usth.demoapp.AdapterUI.FoodAdapter;
import vn.edu.usth.demoapp.AdapterUI.HomeFoodAdapter;
import vn.edu.usth.demoapp.AdapterUI.PhotoAdapter;
import vn.edu.usth.demoapp.ObjectUI.Food;
import vn.edu.usth.demoapp.ObjectUI.Photo;
import vn.edu.usth.demoapp.R;

public class HomeFragment extends Fragment {

    private View mView;
    private ViewPager2 CarouselViewPager;
    private CircleIndicator3 CarouselIndicator;
    private HomeFoodAdapter foodAdapter;

    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewRecent;
    private RecyclerView recyclerViewVideo;



    List<String> imageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        CarouselViewPager = mView.findViewById(R.id.viewPagerImageSlider);
        CarouselIndicator = mView.findViewById(R.id.carousel_indicator);
        recyclerViewCategory = mView.findViewById(R.id.recycler_view_category);
        recyclerViewRecent = mView.findViewById(R.id.recycler_view_recent);
        recyclerViewVideo = mView.findViewById(R.id.recycler_view_video);

        PhotoAdapter photoAdapter = new PhotoAdapter(requireActivity(), getListPhoto());
        CarouselViewPager.setAdapter(photoAdapter);
        CarouselIndicator.setViewPager(CarouselViewPager);
        foodAdapter = new HomeFoodAdapter(requireContext());
        // set recent food(horizontal scroll)
        recyclerViewRecent.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        foodAdapter.setData(getListRecentFood());
        recyclerViewRecent.setAdapter(foodAdapter);

        handleAds();

        return mView;
    }

    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.place_holder, "Steak1"));
        list.add(new Photo(R.drawable.place_holder, "Steak2"));
        list.add(new Photo(R.drawable.place_holder, "Steak3"));
        return list;
    }

    private void handleAds() {
        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean allow_ads = sharedPreferences1.getBoolean("allow_ads", true);
        if(!allow_ads) {
            TextView ads = mView.findViewById(R.id.ad_area);
            if(ads != null) {
                ads.setVisibility(View.GONE);
            }
        }
    }



    private List<Food> getListTypeFood(){
        List<Food> list = new ArrayList<>();
        list.add(new Food(R.drawable.appetizers, "Appetizers", 0, "This is appetizers recipe that you can make at home"));
        list.add(new Food(R.drawable.breakfast, "Breakfast", 0, "This is breakfast recipe that you can make at home"));
        list.add(new Food(R.drawable.main_dish, "Main dish", 0, "This is main dish recipe that you can make at home"));
        list.add(new Food(R.drawable.side_dish, "Side dish", 0, "This is side dish recipe that you can make at home"));
        list.add(new Food(R.drawable.desserts, "Desserts", 0, "This is desserts recipe that you can make at home"));
        list.add(new Food(R.drawable.drinks, "Drinks", 0, "This is drinks recipe that you can make at home"));
        return list;
    }

    private List<Food> getListRecentFood(){
        List<Food> list = new ArrayList<>();
        list.add(new Food(R.drawable.appetizers, "Appetizers", 0, "This is appetizers recipe that you can make at home"));
        list.add(new Food(R.drawable.breakfast, "Breakfast", 0, "This is breakfast recipe that you can make at home"));
        list.add(new Food(R.drawable.main_dish, "Main dish", 0, "This is main dish recipe that you can make at home"));
        list.add(new Food(R.drawable.side_dish, "Side dish", 0, "This is side dish recipe that you can make at home"));
        list.add(new Food(R.drawable.desserts, "Desserts", 0, "This is desserts recipe that you can make at home"));
        list.add(new Food(R.drawable.drinks, "Drinks", 0, "This is drinks recipe that you can make at home"));
        return list;
    }

    private List<Food> getListVideoFood(){
        List<Food> list = new ArrayList<>();
        list.add(new Food(R.drawable.appetizers, "Appetizers", 0, "This is appetizers recipe that you can make at home"));
        list.add(new Food(R.drawable.breakfast, "Breakfast", 0, "This is breakfast recipe that you can make at home"));
        list.add(new Food(R.drawable.main_dish, "Main dish", 0, "This is main dish recipe that you can make at home"));
        list.add(new Food(R.drawable.side_dish, "Side dish", 0, "This is side dish recipe that you can make at home"));
        list.add(new Food(R.drawable.desserts, "Desserts", 0, "This is desserts recipe that you can make at home"));
        list.add(new Food(R.drawable.drinks, "Drinks", 0, "This is drinks recipe that you can make at home"));
        return list;
    }
}