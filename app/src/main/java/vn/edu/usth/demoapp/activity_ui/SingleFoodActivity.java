package vn.edu.usth.demoapp.activity_ui;

import static vn.edu.usth.demoapp.network_controller.Helpers.getTmpValue;
import static vn.edu.usth.demoapp.network_controller.Helpers.isLoggedIn;
import static vn.edu.usth.demoapp.network_controller.Helpers.setTmpValue;
import static vn.edu.usth.demoapp.network_controller.Helpers.trimContent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.Objects;

import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.interface_controller.StatusCallback;
import vn.edu.usth.demoapp.network_controller.FavouriteController;
import vn.edu.usth.demoapp.network_controller.RecipeController;

public class SingleFoodActivity extends AppCompatActivity {

    public Context mContext;

    private Integer foodId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        mContext = getApplicationContext();

        Bundle b = getIntent().getExtras();
        if(b != null && (Objects.equals(b.getString("type"), "recipe_item"))) {
                String itemValue = b.getString("food_name");
                if(itemValue != null) {
                    String titleValue = "Recipe: " + trimContent(itemValue, 20);
                    setTitle(titleValue);


                    setFoodCategory(b.getString("food_category"));
                    setFoodName(itemValue);

                    setPrepTime(b.getString("food_prep_time"));
                    setCookTime(b.getString("food_cook_time"));
                    setLevel(b.getString("food_level"));

                    setDescription(b.getString("food_description"));
                    setFoodIngredientsAndSteps(b.getString("food_html"));
                    setRating(b.getFloat("food_rate"));
                    Log.d("SingleFoodActivity", "onCreate: " + b.getString("food_name") + "isFav_" + b.getBoolean("food_favourite"));
                    if(!Objects.equals(getTmpValue(this, "isFav_" + b.getInt("food_id")), "")) {
                        if(Objects.equals(getTmpValue(this, "isFav_" + b.getInt("food_id")), "true")) {
                            setFavourite(true);
                        } else {
                            setFavourite(false);
                        }
                    } else {
                        setFavourite(b.getBoolean("food_favourite"));
                    }
                    setFoodId(b.getInt("food_id"));

                    // Check if an image URL is provided, and load the image using Glide
                    if (b.containsKey("food_url")) {
                        String imageUrl = b.getString("food_url");
                        setFoodImage(imageUrl);
                    }
                } else {
                    Toast.makeText(mContext, "Error while loading recipe", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }

        handleBack();
        handleFavouriteButton();

    }



    private void setTitle(String title) {
        TextView textView = findViewById(R.id.textViewTitle);
        textView.setText(title);
    }


    private void setFoodCategory(String foodCategory){
        TextView textView = findViewById(R.id.ViewCategory);
        textView.setText(foodCategory);
    }
    private void setFoodName(String name) {
        TextView textView = findViewById(R.id.RecipeNameViewPhotoDetail);
        textView.setText(name);
    }

    private void setFoodImage(String imageUrl) {
        ImageView imageView;
        imageView = findViewById(R.id.RecipeImageViewDetail);
        loadImageUsingVolley(imageUrl, imageView);
    }


    private void setPrepTime(String prepTime) {
        TextView textView = findViewById(R.id.ViewPrepTime);
        textView.setText(prepTime);
    }

    private void setCookTime(String cookTime) {
        TextView textView = findViewById(R.id.ViewCookTime);
        textView.setText(cookTime);
    }

    private void setLevel(String level) {
        TextView textView = findViewById(R.id.ViewLevel);
        textView.setText(level);
    }
    private void setDescription(String description) {
        TextView textView = findViewById(R.id.DescriptionViewPhotoDetail);
        textView.setText(description);
    }

    private void setRating(Float rating) {
        RatingBar ratingBar = findViewById(R.id.ViewRating);
        ratingBar.setRating(rating);
    }

    public void setFavourite(boolean isFavourite) {
        ImageButton button = findViewById(R.id.buttonFavorite);
        if(isFavourite) {
            button.setImageResource(R.drawable.ic_fav);
        } else {
            button.setImageResource(R.drawable.ic_fav_outline);
        }
    }

    private void setFoodIngredientsAndSteps(String detail) {
        WebView webview = findViewById(R.id.FoodWebView);
        webview.loadData(detail, "text/html", null);
    }



//    private void setCategory(String category) {
//        TextView textView = findViewById(R.id.DescriptionViewPhotoDetail);
//        textView.setText(description);
//    }

    private void setFoodId(Integer id) {
        foodId = id;
    }

    private Integer getFoodId() {
        return foodId;
    }

    private void handleBack() {
        findViewById(R.id.buttonBack).setOnClickListener(v -> finish());
    }

    public void loadImageUsingVolley(String url, ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(url, response -> {
            imageView.setImageBitmap(response);
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, error -> {
            Toast.makeText(mContext, "Error while loading image", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(imageRequest);
    }

    private void handleFavouriteButton() {
        ImageButton button = findViewById(R.id.buttonFavorite);
        if(button != null) {
            button.setOnClickListener(v -> {
                if(!isLoggedIn(this)) {
                    Toast.makeText(mContext, "You need to login to use this feature", Toast.LENGTH_SHORT).show();
                    return;
                }

                FavouriteController favouriteController = new FavouriteController();

                if(button.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_fav_outline).getConstantState()) {
                    setFavourite(true);
                    setTmpValue(mContext, "isFav_" + getFoodId(), "true");
                    favouriteController.addToFavourite(this, getIntent().getExtras().getInt("food_id"), new StatusCallback() {
                        @Override
                        public void onStatusOK(boolean status) {
                            Toast.makeText(mContext, "Added to favourite", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(mContext, "Error while adding to favourite", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (button.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_fav).getConstantState()) {
                    setFavourite(false);
                    setTmpValue(mContext, "isFav_" + getFoodId(), "false");
                    favouriteController.removeFromFavourite(this, getIntent().getExtras().getInt("food_id"), new StatusCallback() {
                        @Override
                        public void onStatusOK(boolean status) {
                            Toast.makeText(mContext, "Removed from favourite", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(mContext, "Error while removing from favourite", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(mContext, "Error while adding to favourite", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}