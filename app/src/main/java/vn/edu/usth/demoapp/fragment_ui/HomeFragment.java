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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.adapter_ui.FoodAdapter;
import vn.edu.usth.demoapp.adapter_ui.HomeCategoryAdapter;
import vn.edu.usth.demoapp.adapter_ui.PhotoAdapter;
import vn.edu.usth.demoapp.interface_controller.CategoryListCallback;
import vn.edu.usth.demoapp.interface_controller.PhotoListCallback;
import vn.edu.usth.demoapp.network_controller.CategoryController;
import vn.edu.usth.demoapp.network_controller.FeaturedController;
import vn.edu.usth.demoapp.object_ui.Category;
import vn.edu.usth.demoapp.object_ui.Food;
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

        // Running Carousel
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
            public void onPhotoListReceived(List<Food> photoList) {
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

        getCategoryList(new CategoryListCallback() {
            @Override
            public void onSuccess(List<Category> result) {
                categoryAdapter.setData(result);
                recyclerViewCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onError(VolleyError error) {
                categoryAdapter.setData(new ArrayList<>());
                recyclerViewCategory.setAdapter(categoryAdapter);
            }
        });

        handleAds();
        handleRefresh(homeView);

        return homeView;
    }
    // Runnable
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int nextItem = CarouselViewPager.getCurrentItem() + 1;
            if (nextItem >= CarouselViewPager.getAdapter().getItemCount()) {
                nextItem = 0;
            }
            CarouselViewPager.setCurrentItem(nextItem, true);
        }
    };

    private void getListPhoto(PhotoListCallback callback) {
        FeaturedController featuredController = new FeaturedController();
        FeaturedController.getFeaturedRecipe(requireContext(), new PhotoListCallback() {
            @Override
            public void onPhotoListReceived(List<Food> photoList) {
                callback.onPhotoListReceived(photoList);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });
    }

    private void getCategoryList(CategoryListCallback callback) {
        CategoryController categoryController = new CategoryController();
        categoryController.getCatgoryList(requireContext(), new CategoryListCallback() {
            @Override
            public void onSuccess(List<Category> result) {
                callback.onSuccess(result);
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

    private void handleRefresh(View homeView) {
        SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) homeView.findViewById(R.id.swiperefresh);
        if (swipeContainer == null) {
            return;
        }
        swipeContainer.setOnRefreshListener(() -> {
            swipeContainer.setRefreshing(false);
        });


    }

}