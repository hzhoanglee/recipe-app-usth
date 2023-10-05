package vn.edu.usth.demoapp.fragment_ui;

import static vn.edu.usth.demoapp.network_controller.Helpers.showLoadingDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.demoapp.adapter_ui.CategoryAdapter;
import vn.edu.usth.demoapp.interface_controller.CategoryCallback;
import vn.edu.usth.demoapp.interface_controller.CategoryListCallback;
import vn.edu.usth.demoapp.network_controller.CategoryController;
import vn.edu.usth.demoapp.object_ui.Category;
import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.object_ui.Food;

public class CategoryFragment extends Fragment {

    private List<Category> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Dialog dialog = showLoadingDialog(requireContext());

        CategoryAdapter categoryAdapter;
        RecyclerView rcvCategory;
        View mView;
        mView = inflater.inflate(R.layout.fragment_category, container, false);

        rcvCategory = mView.findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(requireContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        getListCategory(new CategoryCallback(){
            @Override
            public void onCategoryListReceived(List<Category> categoryList) {
                categoryAdapter.setData(categoryList);
                if(dialog != null)
                    dialog.dismiss();
                rcvCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onError(VolleyError error) {
                list = null;
            }
        });
        return mView;

    }

    private void getListCategory(CategoryCallback callback) {
        CategoryController categoryController = new CategoryController();
        categoryController.getCatgoryList(requireContext(), new CategoryListCallback() {
            @Override
            public void onSuccess(List<Category> result) {
                callback.onCategoryListReceived(result);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });
    }


}

