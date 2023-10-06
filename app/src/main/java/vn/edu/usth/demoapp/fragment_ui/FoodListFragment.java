package vn.edu.usth.demoapp.fragment_ui;

import static vn.edu.usth.demoapp.network_controller.Helpers.showLoadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;

import vn.edu.usth.demoapp.adapter_ui.FoodAdapter;
import vn.edu.usth.demoapp.interface_controller.FoodCallback;
import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.network_controller.RecipeController;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.R;

public class FoodListFragment extends Fragment {

    private List<Food> list;

    public FoodListFragment(List <Food> list) {
        this.list = list;
        if (list == null) {
            Log.e("FoodListFragment", "List is null");
        } else {
            Log.e("FoodListFragment", "List is not null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FoodAdapter foodAdapter;
        RecyclerView rcvFood;
        View mView;
        mView = inflater.inflate(R.layout.fragment_explore, container, false);
        rcvFood = mView.findViewById(R.id.rcv_food);
        foodAdapter = new FoodAdapter(requireContext());
        foodAdapter.setHasStableIds(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcvFood.setLayoutManager(gridLayoutManager);
        foodAdapter.setData(getFoodList());
        rcvFood.setAdapter(foodAdapter);
        return mView;
    }

    private List<Food> getFoodList() {
        return this.list;
    }
}