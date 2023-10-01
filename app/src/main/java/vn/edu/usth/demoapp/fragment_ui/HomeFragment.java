package vn.edu.usth.demoapp.fragment_ui;

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


import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import vn.edu.usth.demoapp.adapter_ui.HomeCategoryAdapter;
import vn.edu.usth.demoapp.adapter_ui.HomeFoodAdapter;
import vn.edu.usth.demoapp.adapter_ui.PhotoAdapter;
import vn.edu.usth.demoapp.object_ui.Category;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.object_ui.Photo;
import vn.edu.usth.demoapp.R;

public class HomeFragment extends Fragment {

    private View homeView;
    List<String> imageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerViewVideo;
        RecyclerView recyclerViewRecent;
        RecyclerView recyclerViewCategory;
        HomeCategoryAdapter categoryAdapter;
        HomeFoodAdapter foodAdapter;
        CircleIndicator3 CarouselIndicator;
        ViewPager2 CarouselViewPager;
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        CarouselViewPager = homeView.findViewById(R.id.viewPagerImageSlider);
        CarouselIndicator = homeView.findViewById(R.id.carousel_indicator);
        recyclerViewCategory = homeView.findViewById(R.id.recycler_view_category);
        recyclerViewRecent = homeView.findViewById(R.id.recycler_view_recent);
        recyclerViewVideo = homeView.findViewById(R.id.recycler_view_video);

        PhotoAdapter photoAdapter = new PhotoAdapter(requireActivity(), getListPhoto());
        CarouselViewPager.setAdapter(photoAdapter);
        CarouselIndicator.setViewPager(CarouselViewPager);
        foodAdapter = new HomeFoodAdapter(requireContext());
        // set recent food(horizontal scroll)
        recyclerViewRecent.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        foodAdapter.setData(getListRecentFood());
        recyclerViewRecent.setAdapter(foodAdapter);

        // set category food(horizontal scroll)
        categoryAdapter = new HomeCategoryAdapter(requireContext());
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter.setData(getListTypeFood());
        recyclerViewCategory.setAdapter(categoryAdapter);

        handleAds();

        return homeView;
    }

    private List<Photo> getListPhoto(){
        String imgurl1 = "https://cdn.tgdd.vn/Files/2021/07/29/1371693/steak-la-gi-cac-loai-steak-ngon-va-muc-do-chin-cua-steak-202107292117365026.jpg";

        List<Photo> list = new ArrayList<>();
        list.add(new Photo(imgurl1, "Steak1"));
        list.add(new Photo(imgurl1, "Steak2"));
        list.add(new Photo(imgurl1, "Steak3"));
        return list;
    }

    private void handleAds() {
        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean allow_ads = sharedPreferences1.getBoolean("allow_ads", true);
        if(!allow_ads) {
            TextView ads = homeView.findViewById(R.id.ad_area);
            if(ads != null) {
                ads.setVisibility(View.GONE);
            }
        }
    }



    private List<Category> getListTypeFood(){
        List<Category> list = new ArrayList<>();
        list.add(new Category(R.drawable.appetizers, "Appetizers"));
        list.add(new Category(R.drawable.breakfast, "Breakfast"));
        list.add(new Category(R.drawable.main_dish, "Main dish"));
        list.add(new Category(R.drawable.side_dish, "Side dish"));
        list.add(new Category(R.drawable.desserts, "Desserts"));
        list.add(new Category(R.drawable.drinks, "Drinks"));
        return list;
    }

    private List<Food> getListRecentFood(){
        String tmp_url = "https://cdn.tgdd.vn/Files/2021/07/29/1371693/steak-la-gi-cac-loai-steak-ngon-va-muc-do-chin-cua-steak-202107292117365026.jpg";

        List<Food> list = new ArrayList<>();
        list.add(new Food(tmp_url, "Appetizers", 0, "This is appetizers recipe that you can make at home"));
        list.add(new Food(tmp_url, "Breakfast", 0, "This is breakfast recipe that you can make at home"));
        list.add(new Food(tmp_url, "Main dish", 0, "This is main dish recipe that you can make at home"));
        list.add(new Food(tmp_url, "Side dish", 0, "This is side dish recipe that you can make at home"));
        list.add(new Food(tmp_url, "Desserts", 0, "This is desserts recipe that you can make at home"));
        list.add(new Food(tmp_url, "Drinks", 0, "This is drinks recipe that you can make at home"));
        return list;
    }

    private List<Food> getListVideoFood(){
        String tmp_url = "https://cdn.tgdd.vn/Files/2021/07/29/1371693/steak-la-gi-cac-loai-steak-ngon-va-muc-do-chin-cua-steak-202107292117365026.jpg";

        List<Food> list = new ArrayList<>();
        list.add(new Food(tmp_url, "Appetizers", 0, "This is appetizers recipe that you can make at home"));
        list.add(new Food(tmp_url, "Breakfast", 0, "This is breakfast recipe that you can make at home"));
        list.add(new Food(tmp_url, "Main dish", 0, "This is main dish recipe that you can make at home"));
        list.add(new Food(tmp_url, "Side dish", 0, "This is side dish recipe that you can make at home"));
        list.add(new Food(tmp_url, "Desserts", 0, "This is desserts recipe that you can make at home"));
        list.add(new Food(tmp_url, "Drinks", 0, "This is drinks recipe that you can make at home"));
        return list;
    }
}