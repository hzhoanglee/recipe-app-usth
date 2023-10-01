package vn.edu.usth.demoapp.fragment_ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.demoapp.adapter_ui.FoodAdapter;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.R;

public class ExploreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FoodAdapter foodAdapter;
        RecyclerView rcvFood;
        View mView;
        mView = inflater.inflate(R.layout.fragment_explore, container, false);
        rcvFood = mView.findViewById(R.id.rcv_food);
        foodAdapter = new FoodAdapter(requireContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcvFood.setLayoutManager(gridLayoutManager);
        //List <Food> list = getListFood();

        foodAdapter.setData(getListFood());
        rcvFood.setAdapter(foodAdapter);

        return mView;
    }

    private List<Food> getListFood(){
        String tmp_url = "https://cdn.tgdd.vn/Files/2021/07/29/1371693/steak-la-gi-cac-loai-steak-ngon-va-muc-do-chin-cua-steak-202107292117365026.jpg";
        List<Food> list = new ArrayList<>();
        list.add(new Food(tmp_url, "Appetizers", randomStar(), "This is appetizers recipe that you can make at home"));
        list.add(new Food(tmp_url, "Breakfast", randomStar(), "This is breakfast recipe that you can make at home"));
        list.add(new Food(tmp_url, "Main dish", randomStar(), "This is main dish recipe that you can make at home"));
        list.add(new Food(tmp_url, "Side dish", randomStar(), "This is side dish recipe that you can make at home"));
        list.add(new Food(tmp_url, "Desserts", randomStar(), "This is desserts recipe that you can make at home"));
        list.add(new Food(tmp_url, "Drinks", randomStar(), "This is drinks recipe that you can make at home"));
        list.add(new Food(tmp_url, "Appetizers", randomStar(), "Another appetizers recipe that you can make at home"));
        list.add(new Food(tmp_url, "Breakfast", randomStar(), "Just another breakfast recipe that you can make at home"));
        list.add(new Food(tmp_url, "Main dish", randomStar(), "More main dish recipe that you can make at home"));
        list.add(new Food(tmp_url, "Side dish", randomStar(), "OMG, more side dish recipe that you can make at home"));
        list.add(new Food(tmp_url, "Desserts", randomStar(), "Can you believe it? More desserts recipe that you can make at home"));
        list.add(new Food(tmp_url, "Drinks", randomStar(), "If you are bored, you can make this drinks recipe at home"));
        
        for (int i = 0; i < list.size(); i++) {
            int randomIndexToSwap = (int) (Math.random() * list.size());
            Food temp = list.get(randomIndexToSwap);
            list.set(randomIndexToSwap, list.get(i));
            list.set(i, temp);
        }

        return list;
    }
    
    private float randomStar(){
        return (float) (Math.random() * 2 + 3);
    }
}