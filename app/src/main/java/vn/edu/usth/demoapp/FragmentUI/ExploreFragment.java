package vn.edu.usth.demoapp.FragmentUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.edu.usth.demoapp.AdapterUI.CategoryAdapter;
import vn.edu.usth.demoapp.AdapterUI.FoodAdapter;
import vn.edu.usth.demoapp.ObjectUI.Category;
import vn.edu.usth.demoapp.ObjectUI.Food;
import vn.edu.usth.demoapp.R;

public class ExploreFragment extends Fragment {
    private RecyclerView rcvFood;
    private FoodAdapter foodAdapter;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_explore, container, false);
        rcvFood = mView.findViewById(R.id.rcv_food);
        foodAdapter = new FoodAdapter(requireContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcvFood.setLayoutManager(gridLayoutManager);
        List <Food> list = getListFood();

        foodAdapter.setData(getListFood());
        rcvFood.setAdapter(foodAdapter);

        return mView;
    }

    private List<Food> getListFood(){
        List<Food> list = new ArrayList<>();
        list.add(new Food(R.drawable.appetizers, "Appetizers", randomStar(), "This is appetizers recipe that you can make at home"));
        list.add(new Food(R.drawable.breakfast, "Breakfast", randomStar(), "This is breakfast recipe that you can make at home"));
        list.add(new Food(R.drawable.main_dish, "Main dish", randomStar(), "This is main dish recipe that you can make at home"));
        list.add(new Food(R.drawable.side_dish, "Side dish", randomStar(), "This is side dish recipe that you can make at home"));
        list.add(new Food(R.drawable.desserts, "Desserts", randomStar(), "This is desserts recipe that you can make at home"));
        list.add(new Food(R.drawable.drinks, "Drinks", randomStar(), "This is drinks recipe that you can make at home"));
        list.add(new Food(R.drawable.appetizers, "Appetizers", randomStar(), "Another appetizers recipe that you can make at home"));
        list.add(new Food(R.drawable.breakfast, "Breakfast", randomStar(), "Just another breakfast recipe that you can make at home"));
        list.add(new Food(R.drawable.main_dish, "Main dish", randomStar(), "More main dish recipe that you can make at home"));
        list.add(new Food(R.drawable.side_dish, "Side dish", randomStar(), "OMG, more side dish recipe that you can make at home"));
        list.add(new Food(R.drawable.desserts, "Desserts", randomStar(), "Can you believe it? More desserts recipe that you can make at home"));
        list.add(new Food(R.drawable.drinks, "Drinks", randomStar(), "If you are bored, you can make this drinks recipe at home"));
        
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