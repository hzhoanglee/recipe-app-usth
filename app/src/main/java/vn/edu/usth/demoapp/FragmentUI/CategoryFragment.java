package vn.edu.usth.demoapp.FragmentUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.demoapp.R;

public class CategoryFragment extends Fragment {
    private RecyclerView rcvDish;
    private DishAdapter dishAdapter;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_category, container, false);

        rcvDish = mView.findViewById(R.id.rcv_dish);
        //dishAdapter = new DishAdapter(this);
        dishAdapter = new DishAdapter(requireContext());

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcvDish.setLayoutManager(linearLayoutManager);

        dishAdapter.setData(getListDish());
        rcvDish.setAdapter(dishAdapter);
        return mView;
    }
    private List<Dish> getListDish(){
        List<Dish> list = new ArrayList<>();
        list.add(new Dish(R.drawable.person_cooking1, "Appetizers"));
        list.add(new Dish(R.drawable.person_cooking2, "Breakfast"));
        list.add(new Dish(R.drawable.person_cooking3, "Min dish"));
        list.add(new Dish(R.drawable.person_cooking4, "Side dish"));
        list.add(new Dish(R.drawable.person_cooking1, "Desserts"));
        list.add(new Dish(R.drawable.person_cooking2, "Drinks"));

        return list;
    }

}

