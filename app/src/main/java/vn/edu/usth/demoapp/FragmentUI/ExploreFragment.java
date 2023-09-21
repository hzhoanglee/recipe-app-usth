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
        //categoryAdapter = new CategoryAdapter(this);
        foodAdapter = new FoodAdapter(requireContext());

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcvFood.setLayoutManager(gridLayoutManager);
        List <Food> list = getListFood();

        foodAdapter.setData(getListFood());
        rcvFood.setAdapter(foodAdapter);

        return mView;
    }

    private List<Food> getListFood(){
        List<Food> list = new ArrayList<>();
        list.add(new Food(R.drawable.appetizers, "Appetizers"));
        list.add(new Food(R.drawable.breakfast, "Breakfast"));
        list.add(new Food(R.drawable.main_dish, "Main dish"));
        list.add(new Food(R.drawable.side_dish, "Side dish"));
        list.add(new Food(R.drawable.desserts, "Desserts"));
        list.add(new Food(R.drawable.drinks, "Drinks"));

        return list;
    }
}