package vn.edu.usth.demoapp.activity_ui;

import static vn.edu.usth.demoapp.network_controller.Helpers.showLoadingDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;
import java.util.Objects;

import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.fragment_ui.FoodListFragment;
import vn.edu.usth.demoapp.interface_controller.FoodCallback;
import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.network_controller.CategoryController;
import vn.edu.usth.demoapp.network_controller.RecipeController;
import vn.edu.usth.demoapp.object_ui.Food;

public class SearchResultActivity extends AppCompatActivity {

    private List<Food> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Dialog dialog = showLoadingDialog(this);
        assert dialog != null;
        dialog.show();

        Bundle b = getIntent().getExtras();
        String value = "";
        String title = "";
        String action = "";
        if(b != null) {
            String search_param = b.getString("search_param");
            if (Objects.equals(b.getString("type"), "category")) {
                value = "Category: " + search_param;
                title = getString(R.string.title_nav_category);
                getListFoodByCategory(new FoodCallback() {
                    @Override
                    public void onFoodListReceived(List<Food> foodList) {
                        setListFood(foodList);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FoodListFragment(getListFood())).commit();
                    }
                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(SearchResultActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                        setListFood(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FoodListFragment(getListFood())).commit();
                    }
                }, b.getInt("category_id"));
            }
            else {
                value = "Search result for: " + search_param;
                title = getString(R.string.Search);
                getListFood(new FoodCallback() {
                    @Override
                    public void onFoodListReceived(List<Food> foodList) {
                        setListFood(foodList);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FoodListFragment(getListFood())).commit();
                    }
                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(SearchResultActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                        setListFood(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new FoodListFragment(getListFood())).commit();
                    }
                }, search_param);
            }
            if (search_param.length() > 12) {
                search_param = search_param.substring(0, 12) + "...";
            }
            setTitle(title + ": " + search_param);
        }
        if(dialog != null)
            dialog.dismiss();


        TextView query_text = findViewById(R.id.query_text);
        query_text.setText(value);
        handleBack();

    }

    private void setTitle(String title) {
        TextView textView = findViewById(R.id.textViewTitle);
        textView.setText(title);
    }

    private void handleBack() {
        findViewById(R.id.SettingBackButton).setOnClickListener(v -> finish());
    }

    private void getListFood(FoodCallback callback, String searchQuery) {
        RecipeController recipeController = new RecipeController();
        recipeController.getSearchRecipe(this, searchQuery, new FoodListCallback() {
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

    private void getListFoodByCategory(FoodCallback callback, int categoryId) {
        CategoryController categoryController = new CategoryController();
        categoryController.getCategoryRecipe(this, categoryId, new FoodListCallback() {
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

    private void setListFood(List<Food> list) {
        this.list = list;
    }

    private List<Food> getListFood() {
        return this.list;
    }
}