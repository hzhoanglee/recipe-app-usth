package vn.edu.usth.demoapp.fragment_ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.adapter_ui.FoodAdapter;
import vn.edu.usth.demoapp.adapter_ui.HomeCategoryAdapter;
import vn.edu.usth.demoapp.adapter_ui.PhotoAdapter;
import vn.edu.usth.demoapp.interface_controller.PhotoListCallback;
import vn.edu.usth.demoapp.network_controller.FeaturedController;
import vn.edu.usth.demoapp.object_ui.Category;
import vn.edu.usth.demoapp.object_ui.Photo;
import vn.edu.usth.demoapp.object_ui.RecentList;

public class HomeFragment extends Fragment {

    private View homeView;
    private Handler sliderHandler = new Handler();
    private ViewPager2 CarouselViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerViewRecent;
        RecyclerView recyclerViewCategory;
        HomeCategoryAdapter categoryAdapter;
        FoodAdapter foodAdapter;
        CircleIndicator3 CarouselIndicator;
        //ViewPager2 CarouselViewPager;
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        CarouselViewPager = homeView.findViewById(R.id.viewPagerImageSlider);
        CarouselIndicator = homeView.findViewById(R.id.carousel_indicator);
        recyclerViewCategory = homeView.findViewById(R.id.recycler_view_category);
        recyclerViewRecent = homeView.findViewById(R.id.recycler_view_recent);

        CarouselViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        getListPhoto(new PhotoListCallback() {
            @Override
            public void onPhotoListReceived(List<Photo> photoList) {
                PhotoAdapter photoAdapter = new PhotoAdapter(requireActivity(), photoList);
                CarouselViewPager.setAdapter(photoAdapter);
                CarouselIndicator.setViewPager(CarouselViewPager);
            }

            @Override
            public void onError(VolleyError error) {
                PhotoAdapter photoAdapter = new PhotoAdapter(requireActivity(), null);
                CarouselViewPager.setAdapter(photoAdapter);
                CarouselIndicator.setViewPager(CarouselViewPager);
            }
        });


        foodAdapter = new FoodAdapter(requireContext());
        // set recent food(horizontal scroll)
        recyclerViewRecent.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        foodAdapter.setData(RecentList.getRecentlyClickedItemsLiveData().getValue());
        recyclerViewRecent.setAdapter(foodAdapter);

        // set category food(horizontal scroll)
        categoryAdapter = new HomeCategoryAdapter(requireContext());
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter.setData(getListTypeFood());
        recyclerViewCategory.setAdapter(categoryAdapter);

        handleAds();

        return homeView;
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            CarouselViewPager.setCurrentItem(CarouselViewPager.getCurrentItem() +1);
        }
    };

    private void getListPhoto(PhotoListCallback callback) {
        FeaturedController featuredController = new FeaturedController();
        FeaturedController.getFeaturedRecipe(requireContext(), new PhotoListCallback() {
            @Override
            public void onPhotoListReceived(List<Photo> photoList) {
                callback.onPhotoListReceived(photoList);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });
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
//        list.add(new Category(R.drawable.appetizers, "Appetizers"));
//        list.add(new Category(R.drawable.breakfast, "Breakfast"));
//        list.add(new Category(R.drawable.main_dish, "Main dish"));
//        list.add(new Category(R.drawable.side_dish, "Side dish"));
//        list.add(new Category(R.drawable.desserts, "Desserts"));
//        list.add(new Category(R.drawable.drinks, "Drinks"));
        return list;
    }

}