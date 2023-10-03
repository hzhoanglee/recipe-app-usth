package vn.edu.usth.demoapp.activity_ui;

import static vn.edu.usth.demoapp.network_controller.Helpers.trimContent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
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

public class SingleFoodActivity extends AppCompatActivity {

    public Context mContext;


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
                    setFoodName(itemValue);
                    setDescription(b.getString("food_description"));
                    setFoodIngredientsAndSteps(b.getString("food_html"));
                    setRating(b.getFloat("food_rate"));

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

    }

    private void setTitle(String title) {
        TextView textView = findViewById(R.id.textViewTitle);
        textView.setText(title);
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

    private void setDescription(String description) {
        TextView textView = findViewById(R.id.DescriptionViewPhotoDetail);
        textView.setText(description);
    }

    private void setRating(Float rating) {
        RatingBar ratingBar = findViewById(R.id.ViewRating);
        ratingBar.setRating(rating);
    }

    private void setFoodIngredientsAndSteps(String detail) {
        WebView webview = findViewById(R.id.FoodWebView);
        webview.loadData(detail, "text/html", null);
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

}