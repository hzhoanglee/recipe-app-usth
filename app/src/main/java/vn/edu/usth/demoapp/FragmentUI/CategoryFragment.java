package vn.edu.usth.demoapp.FragmentUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.demoapp.AdapterUI.CategoryAdapter;
import vn.edu.usth.demoapp.ObjectUI.Category;
import vn.edu.usth.demoapp.R;

public class CategoryFragment extends Fragment {
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_category, container, false);

        rcvCategory = mView.findViewById(R.id.rcv_category);
        //categoryAdapter = new CategoryAdapter(this);
        categoryAdapter = new CategoryAdapter(requireContext());

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);
        List <Category> list = getListCategory();

        categoryAdapter.setData(list);
        rcvCategory.setAdapter(categoryAdapter);

        return mView;

    }


    private List<Category> getListCategory(){
        List<Category> list = new ArrayList<>();
        list.add(new Category(R.drawable.appetizers, "Appetizers"));
        list.add(new Category(R.drawable.breakfast, "Breakfast"));
        list.add(new Category(R.drawable.main_dish, "Main dish"));
        list.add(new Category(R.drawable.side_dish, "Side dish"));
        list.add(new Category(R.drawable.desserts, "Desserts"));
        list.add(new Category(R.drawable.drinks, "Drinks"));

        return list;
    }

}

