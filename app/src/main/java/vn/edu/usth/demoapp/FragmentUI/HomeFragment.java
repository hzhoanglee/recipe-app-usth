package vn.edu.usth.demoapp.FragmentUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import vn.edu.usth.demoapp.AdapterUI.PhotoAdapter;
import vn.edu.usth.demoapp.ObjectUI.Photo;
import vn.edu.usth.demoapp.R;

public class HomeFragment extends Fragment {

    private View mView;
    private ViewPager2 CarouselViewPager;
    private CircleIndicator3 CarouselIndicator;


    List<String> imageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        CarouselViewPager = mView.findViewById(R.id.viewPagerImageSlider);
        CarouselIndicator = mView.findViewById(R.id.carousel_indicator);

        PhotoAdapter photoAdapter = new PhotoAdapter(requireActivity(), getListPhoto());
        CarouselViewPager.setAdapter(photoAdapter);
        CarouselIndicator.setViewPager(CarouselViewPager);

        return mView;
    }

    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.place_holder, "Steak1"));
        list.add(new Photo(R.drawable.place_holder, "Steak2"));
        list.add(new Photo(R.drawable.place_holder, "Steak3"));
        return list;
    }
}