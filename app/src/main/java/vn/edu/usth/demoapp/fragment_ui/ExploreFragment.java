package vn.edu.usth.demoapp.fragment_ui;

import static vn.edu.usth.demoapp.network_controller.Helpers.showLoadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.demoapp.adapter_ui.FoodAdapter;
import vn.edu.usth.demoapp.interface_controller.FoodCallback;
import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.network_controller.RecipeController;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.R;

//cat log
import android.util.Log;

public class ExploreFragment extends Fragment {

    private List<Food> listFood = new ArrayList<>();

    private FoodAdapter foodAdapter;
    private SwipeRefreshLayout swipeContainer;
    private int currentPage = 1;
    private boolean isLoading = false;
    private ProgressBar progressBar;

    private Button btnLoadMore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Dialog dialog = showLoadingDialog(requireContext());
        RecyclerView rcvFood;
        View mView;

        mView = inflater.inflate(R.layout.fragment_explore, container, false);
        rcvFood = mView.findViewById(R.id.rcv_food);
        foodAdapter = new FoodAdapter(requireContext());
        foodAdapter.setHasStableIds(true);
        foodAdapter.setData(listFood);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        rcvFood.setLayoutManager(gridLayoutManager);
        getListFood(currentPage, new FoodCallback() {
            @Override
            public void onFoodListReceived(List<Food> foodList) {
                for (Food food : foodList) {
                    listFood.add(food);
                }
                foodAdapter.setData(listFood);
                if(dialog != null)
                    dialog.dismiss();
                rcvFood.setAdapter(foodAdapter);
            }
            @Override
            public void onError(VolleyError error) {
                listFood = null;
            }
        });

        handleRefresh(mView, dialog);

        btnLoadMore = mView.findViewById(R.id.btnLoadMore);

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreData(mView);
            }
        });

        rcvFood.setItemAnimator(null);

        // Find the ProgressBar by ID
        progressBar = mView.findViewById(R.id.progressBar);

        return mView;
    }

    private void getListFood(int page, FoodCallback callback) {
        RecipeController recipeController = new RecipeController();
        recipeController.getExploreList(requireContext(), page, new FoodListCallback() {
            @Override
            public void onSuccess(List<Food> result) {
                callback.onFoodListReceived(result);
            }

            @Override
            public void onError(VolleyError error) {
                callback.onError(error);
            }
        });
    }

    private void handleRefresh(View mview, Dialog dialog) {
        swipeContainer = (SwipeRefreshLayout) mview.findViewById(R.id.swiperefresh);
        if (swipeContainer == null) {
            return;
        }
        swipeContainer.setOnRefreshListener(() -> {
            dialog.show();
            currentPage = 1;
            btnLoadMore.setVisibility(View.VISIBLE);
            getListFood(currentPage, new FoodCallback() {
                @Override
                public void onFoodListReceived(List<Food> foodList) {
                    listFood.clear();
                    for (Food food : foodList) {
                        listFood.add(food);
                    }
                    foodAdapter.setData(listFood);
                    RecyclerView rcvFood = requireActivity().findViewById(R.id.rcv_food);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
                    rcvFood.setLayoutManager(gridLayoutManager);
                    rcvFood.setAdapter(foodAdapter);
                    dialog.dismiss();
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void loadMoreData(View view) {
        // Increment the currentPage
        currentPage++;

        if (currentPage == 4){
            btnLoadMore.setVisibility(View.GONE);
        }

        // Show a loading dialog
        Dialog dialog = showLoadingDialog(requireContext());

        // Fetch data for the updated currentPage
        getListFood(currentPage, new FoodCallback() {
            @Override
            public void onFoodListReceived(List<Food> newFoodList) {
                // Append the new items to the existing foodList
                for (Food food : newFoodList) {
                    listFood.add(food);
                }
                foodAdapter.setData(listFood);

                // Dismiss the loading dialog
                dialog.dismiss();
            }

            @Override
            public void onError(VolleyError error) {
                // Handle the error, e.g., show a toast message
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}